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

The testing for the Library project will consist of Unit and System
test levels. It is hoped that there will be at least one full time
independent test person for system testing.

UNIT Testing will be done automatically via
a testing framework. The developers working on the Library project
will have to create automatic Unit tests for the appropriate project design entities.
The tests should cover as much test cases as possible.

SYSTEM Testing will be performed by the test manager and development
team leader with assistance from the individual developers as required. No specific test tools
are available for this project.

## Item pass/fail criteria

A feature tested by system testing will be considered to have passed the test on the condition that the application
server returns an HTTP Response with either the 2xx or the 3xx code.

A features tested by unit testing will have to define their own criteria for success and failure.

## Suspension criteria and resumption requirements

* SQL database connection error

  If the above condition occurs, any testing processes should be immediately stopped. The testing may continue only if
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

## Approvals

I approve of this test plan, very nice!