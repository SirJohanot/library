# Test Plan

## Test Plan Identifier LIBRARY-1.0SNAPSHOT-TEST

## Introduction

This is the Test Plan for the Library project. This plan will
address all items and elements related to the core functionality of the application. The primary
focus of this plan is to ensure that the Library project is devoid of any and all bugs and possible system glitches.

## Test items

The following is a list, by version and release, of the items to be tested:

* Library application, Version 1.0-SNAPSHOT
* Library MySQL database, Version 1.0-SNAPSHOT

## Features to be tested

The following is a list of the areas to be focused on during testing of the application.

* Authentication
* Account creation
* Books management (for the admin role)
* Users management (for the admin role)
* Book orders management (for the librarian role)
* Book renting (for the reader role)
* Search items filtering
* Localisation
* UI/UX

## Approach

The testing for the Library project will consist of UNIT and System
test levels. It is hoped that there will be at least one full time
independent test person for system testing.

* UNIT Testing will be done automatically via
  a testing framework. The developers working on the Library project
  will have to create automatic Unit tests for the appropriate project design entities.
  The tests should cover as much test cases as possible.

* SYSTEM Testing will be performed by the test manager and development
  team leader with assistance from the individual developers as required. No specific test tools
  are available for this project.

## Item pass/fail criteria

The test process will be completed once all the item's features pass the tests.

Feature tested by system testing will
be considered to have passed the test on the condition that the application
server returns an HTTP Response with either the 2xx or the 3xx code.

Features tested by unit testing will have to define their own criteria for success and failure.

## Suspension criteria and resumption requirements

* SQL database connection error

  Any testing processes must be immediately stopped. The testing may continue only if
  the database connectivity issue is resolved and the application can once again correctly communicate with the database

## Test deliverables

* Unit tests documentation
* System test plan
* Report mock-ups
* Defect/Incident reports and summaries
* Test logs and turnover reports

## Environmental needs

The following is a list of the prerequisites for the test environment during testing of the application.

* A running instance of Apache Tomcat V9.0
* A running instance of a MySQL80 database
* A web browser

## Responsibilities

|                                               | Dev Team |
|-----------------------------------------------|:--------:|
| Acceptance test Documentation & Execution     |    X     |
| System/Integration test Documentation & Exec. |    X     |
| Unit test documentation & execution           |    X     |
| System Design Reviews                         |    X     |
| Detail Design Reviews                         |    X     |
| Test procedures and rules                     |    X     |
| Screen & Report prototype reviews             |    X     |
| Change Control and regression testing         |    X     |

## Staffing and training needs

It is preferred that there will be at least one (1) full time tester assigned to the project
for the system/integration and acceptance testing phases of the project. This will
require assignment of a person part-time at the beginning of the project to participate
in reviews etc... and approximately four months into the project they would be
assigned full time. If a separate test person is not available the project manager/test
manager will assume this role.

In order to provide complete and proper testing the following areas need to be addressed in
terms of training.

* Personnel responsible for UNIT testing should possess the knowledge of Java testing frameworks and the Maven build
  tool

* Personnel responsible for System testing should possess basic knowledge of web browser usage, databases and the http
  protocol

## Schedule

Time has been allocated within the project plan for the following testing activities. The
specific dates and times for each activity are defined in the project plan timeline. The persons
required for each process are detailed in the project timeline and plan as well. Coordination
of the personnel required for each task, test team, development team, management and
customer will be handled by the project manager in conjunction with the development and test
team leaders.

* Review of Requirements document by test team personnel (with other team members) and initial creation of Inventory
  classes, subclasses and objectives.

* Development of Master test plan by test manager and test with time allocated for at least two reviews of the plan.

* Development of System test plan

* Development of UNIT test plan, as well as the UNIT tests themselves

* UNIT test time within the development process

* Time allocated for System test processes.

## Approvals

| Project Developer - SirJohanot |     |
|--------------------------------|-----|
