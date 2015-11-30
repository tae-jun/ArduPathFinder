#ifndef ACTIONS_H
#define ACTIONS_H

#define TIME_RATIO        10

#define SERVO             4

#define LEFT_MOTOR_BACK   8
#define LEFT_MOTOR_GO     9

#define RIGHT_MOTOR_GO    10
#define RIGHT_MOTOR_BACK  11

enum State {
  GO_STRAIGHT,
  TURN_RIGHT,
  TURN_LEFT,
  STOP
};

void turnHead(int degree);

void run(int time);

void brake(int time);

void left(int time);

void spin_left(int time);

void right(int time);

void spin_right(int time);

#endif
