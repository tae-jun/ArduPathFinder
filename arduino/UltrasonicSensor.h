#ifndef ULTRASONIC_SENSOR_H
#define ULTRASONIC_SENSOR_H

#include "Arduino.h"

#define FRONT_TRIG  A0
#define FRONT_ECHO  A1
#define LEFT_TRIG   A2
#define LEFT_ECHO   A3
#define RIGHT_TRIG  A4
#define RIGHT_ECHO  A5

class UltrasonicSensor {
  public:
    UltrasonicSensor(int trig, int echo);
    int getDistance();
  private:
    int trig;
    int echo;
};

#endif
