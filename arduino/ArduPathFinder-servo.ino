#include<SoftwareSerial.h>
#include "Timer.h"
#include "UltrasonicSensor.h"
#include "Actions.h"

#define MIN_DISTANCE  40

/* State */

int state = STOP;

/* Bluetooth */
#define BTS_RX  3
#define BTS_TX  2
SoftwareSerial BTSerial(BTS_TX, BTS_RX);

/* Ultrasonic Sensor */
UltrasonicSensor frontUS(FRONT_TRIG, FRONT_ECHO);
UltrasonicSensor leftUS(LEFT_TRIG, LEFT_ECHO);
UltrasonicSensor rightUS(RIGHT_TRIG, RIGHT_ECHO);

// Heartbeat
Timer t;
#define HEARTBEAT_PERIOD  1000
boolean isTimeToSend = true;
int heartbeatCount = 100;
void clock() {
  isTimeToSend = true;
}

void setup()
{
  // Serial setup
  Serial.begin(9600);
  // Bluetooth setup
  BTSerial.begin(9600);
  // Heartbeat setup
  t.every(HEARTBEAT_PERIOD, clock);
  // Motors
  pinMode(LEFT_MOTOR_GO, OUTPUT);
  pinMode(LEFT_MOTOR_BACK, OUTPUT);
  pinMode(RIGHT_MOTOR_GO, OUTPUT);
  pinMode(RIGHT_MOTOR_BACK, OUTPUT);
  pinMode(SERVO, OUTPUT);
  // Look straight
  turnHead(90);
}

int nextState;

int lWheel = 0;
int rWheel = 0;

int fDis;
int lDis;
int rDis;

double degree = 90.0;

void loop() {
  t.update();   // X

  Serial.print("heartbeatCount: ");
  Serial.println(heartbeatCount);

  // X
  if (heartbeatCount > 0) {
    Serial.println("Check heartbeat");
    while (!BTSerial.available())
      brake(1);
    // Heartbeat recovered
    while (BTSerial.available())
      BTSerial.read();
    heartbeatCount = 0;
  }

  // 돌았을 경우 전 범위 스캔
  if (state == TURN_LEFT || state == TURN_RIGHT) {
    fDis = 1000;
    for (degree = 45; degree <= 135; degree += 22.5) {
      turnHead(degree);
      int tmp = frontUS.getDistance();
      fDis = tmp < fDis ? tmp : fDis;
      if (fDis < MIN_DISTANCE)
        break;
    }
  }
  // 안 돌았을 경우 머리 회전 후 거리 측정
  else {
    turnHead(degree);
    fDis = frontUS.getDistance();
  }
  // 좌우 거리 측정
  lDis = leftUS.getDistance();
  rDis = rightUS.getDistance();
  // 측정 거리에 따라 행동 결정
  if (fDis < MIN_DISTANCE && degree > 90)
    nextState = TURN_RIGHT;
  else if (fDis < MIN_DISTANCE && degree <= 90)
    nextState = TURN_LEFT;
  else if (fDis < MIN_DISTANCE && rDis > MIN_DISTANCE)
    nextState = TURN_RIGHT;
  else if (fDis < MIN_DISTANCE && lDis > MIN_DISTANCE)
    nextState = TURN_LEFT;
  else if (lDis < 20)
    nextState = TURN_RIGHT;
  else if (rDis < 20)
    nextState = TURN_LEFT;
  else if (fDis >= MIN_DISTANCE)
    nextState = GO_STRAIGHT;
  else
    nextState = STOP;

  // Send heartbeat
  if (isTimeToSend || state != nextState)
    sendHeartbeat();

  Serial.print("next state: ");
  printState(nextState);

  // 행동
  int time = 0;
  switch (nextState) {
    case GO_STRAIGHT:
      time = 1;
      run(1);
      lWheel += time;
      rWheel += time;
      break;

    case TURN_LEFT:
      time = 10;
      spin_left(time);
      rWheel += time;
      brake(1);
      break;

    case TURN_RIGHT:
      time = 10;
      spin_right(time);
      lWheel += time;
      brake(1);
      break;

    case STOP:
      brake(1);
      break;
  }
  // 다음 머리 각도 결정
  if (degree >= 135.0)
    degree = 45.0;
  else
    degree += 22.5;
  // 상태 업데이트
  state = nextState;
  Serial.println();
}

void sendHeartbeat() {
  isTimeToSend = false;

  BTSerial.print(lWheel * TIME_RATIO);
  BTSerial.print(",");
  BTSerial.print(rWheel * TIME_RATIO);
  BTSerial.print(",");
  BTSerial.print(fDis);
  BTSerial.print(",");
  BTSerial.print(lDis);
  BTSerial.print(",");
  BTSerial.print(rDis);
  BTSerial.print(",");
  BTSerial.print((int) degree);
  BTSerial.println();

  heartbeatCount++;

  Serial.print("BTSerial->");
  Serial.print(lWheel * TIME_RATIO);
  Serial.print(",");
  Serial.print(rWheel * TIME_RATIO);
  Serial.print(",");
  Serial.print(fDis);
  Serial.print(",");
  Serial.print(lDis);
  Serial.print(",");
  Serial.print(rDis);
  Serial.print(",");
  Serial.print((int) degree);
  Serial.println();

  lWheel = 0;
  rWheel = 0;
}

void printState(int state) {
  if (state == GO_STRAIGHT)
    Serial.println("GO_STRAIGHT");
  else if (state == TURN_LEFT)
    Serial.println("TURN_LEFT");
  else if (state == TURN_RIGHT)
    Serial.println("TURN_RIGHT");
  else if (state == STOP)
    Serial.println("STOP");
}

