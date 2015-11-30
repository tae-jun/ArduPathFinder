#include "Arduino.h"
#include "Actions.h"


int pulsewidth;
void servopulse(int myangle)
{
  pulsewidth = (myangle * 11) + 500; //500-2480
  digitalWrite(SERVO, HIGH);
  delayMicroseconds(pulsewidth);
  digitalWrite(SERVO, LOW);
  delay(20 - pulsewidth / 1000);
}

void turnHead(int degree) {
  for (int i = 0; i <= 5; i++)
    servopulse(degree);
}

void run(int time)
{
  digitalWrite(RIGHT_MOTOR_GO, HIGH);
  digitalWrite(RIGHT_MOTOR_BACK, LOW);
  analogWrite(RIGHT_MOTOR_GO, 101);
  analogWrite(RIGHT_MOTOR_BACK, 0);
  digitalWrite(LEFT_MOTOR_GO, HIGH);
  digitalWrite(LEFT_MOTOR_BACK, LOW);
  analogWrite(LEFT_MOTOR_GO, 100);
  analogWrite(LEFT_MOTOR_BACK, 0);
  delay(time * TIME_RATIO);
}

void brake(int time)
{
  digitalWrite(RIGHT_MOTOR_GO, LOW);
  digitalWrite(RIGHT_MOTOR_BACK, LOW);
  digitalWrite(LEFT_MOTOR_GO, LOW);
  digitalWrite(LEFT_MOTOR_BACK, LOW);
  delay(time * TIME_RATIO);
}


void left(int time)
{
  digitalWrite(RIGHT_MOTOR_GO, HIGH);
  digitalWrite(RIGHT_MOTOR_BACK, LOW);
  analogWrite(RIGHT_MOTOR_GO, 100);
  analogWrite(RIGHT_MOTOR_BACK, 0);
  digitalWrite(LEFT_MOTOR_GO, LOW);
  digitalWrite(LEFT_MOTOR_BACK, LOW);
  analogWrite(LEFT_MOTOR_GO, 0);
  analogWrite(LEFT_MOTOR_BACK, 0);
  delay(time * TIME_RATIO);
}

void spin_left(int time)
{
  digitalWrite(RIGHT_MOTOR_GO, HIGH);
  digitalWrite(RIGHT_MOTOR_BACK, LOW);
  analogWrite(RIGHT_MOTOR_GO, 200);
  analogWrite(RIGHT_MOTOR_BACK, 0);
  digitalWrite(LEFT_MOTOR_GO, LOW);
  digitalWrite(LEFT_MOTOR_BACK, HIGH);
  analogWrite(LEFT_MOTOR_GO, 0);
  analogWrite(LEFT_MOTOR_BACK, 200);
  delay(time * TIME_RATIO);
}

void right(int time)

{
  digitalWrite(RIGHT_MOTOR_GO, LOW);
  digitalWrite(RIGHT_MOTOR_BACK, LOW);
  analogWrite(RIGHT_MOTOR_GO, 0);
  analogWrite(RIGHT_MOTOR_BACK, 0);
  digitalWrite(LEFT_MOTOR_GO, HIGH);
  digitalWrite(LEFT_MOTOR_BACK, LOW);
  analogWrite(LEFT_MOTOR_GO, 100);
  analogWrite(LEFT_MOTOR_BACK, 0);
  delay(time * TIME_RATIO);
}

void spin_right(int time)
{
  digitalWrite(RIGHT_MOTOR_GO, LOW);
  digitalWrite(RIGHT_MOTOR_BACK, HIGH);
  analogWrite(RIGHT_MOTOR_GO, 0);
  analogWrite(RIGHT_MOTOR_BACK, 200);
  digitalWrite(LEFT_MOTOR_GO, HIGH);
  digitalWrite(LEFT_MOTOR_BACK, LOW);
  analogWrite(LEFT_MOTOR_GO, 200);
  analogWrite(LEFT_MOTOR_BACK, 0);
  delay(time * TIME_RATIO);
}

