# Elevator Scheduling Simulator

This repository contains Elevator Scheduling Simulator - a simple terminal simulator of elevators.

The algorithm used for scheduling in this system is an optimized version of FCFS scheduling algorithm.

This application uses features available from _Java SE 14_.

To **run** the application use:
  ```shell
  git clone https://github.com/tmKcode/elevator-scheduling-simulator.git
  cd elevator-manager/
  javac --class-path ./src/ ./src/manager/elevator/Simulation.java
  java --class-path ./src/ ./src/manager/elevator/Simulation.java
  ```
The project consists of 5 subpackages:
* _door_, _floor_ - for visual purposes
* _elevator_ - implements movement of elevator cabs and visuals of elevator shafts
* _dispatcher_ - implements floor calls dispatcher
* _system_ - implements connections between elements of the problem

and _Simulation_ class in main package, which implements I/O connection betweeen the system and the user.
