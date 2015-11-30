#include "UltrasonicSensor.h"


UltrasonicSensor::UltrasonicSensor(int trig, int echo) {
  this->trig = trig;
  this->echo = echo;
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
}

int UltrasonicSensor::getDistance() {
  digitalWrite(this->trig, LOW);
  delayMicroseconds(2);
  digitalWrite(this->trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(this->trig, LOW);

  return pulseIn(this->echo, HIGH) / 58;
}
