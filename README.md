# Outdora
![FigmaMockup](/docs/images/graphic.png)

Outdora is a new dating app designed for the outdoor adventure seekers. 
Whether you're into hiking in the forest, kayaking in the streams, rock climbing in the mountains, or just exploring new trails, 
Outdora helps match you with other like-minded adventurers who share your passion for the great outdoors. 
Our app goes beyond basic metrics to ensure people find a partner who truly understands your love for adventure.

Note: This application is the final project for the Emerging Software Engineering Processes graduate class at Kennesaw State University, built during Summer Semester 2024

This project can be accessed live on the web at this link: (TBC)

## Table of Contents
- [Feature Overview](#feature-overview)
- [Architectural Overview](#architectural-overview)
- [Kanban Board](#project-kanban-board)
- [Team Roster](#team-roster)
- [Runbook](#how-to-start-the-starter-project)
- [Product Vision](/docs/ProductVision.md)
- [Scrum Meeting Transcripts](/docs/scrum_transcripts)
- [Sprint Review Recordings](/docs/sprint_reviews)

### Feature Overview
Note: This is a copy from the initial project description, flesh this out as project is realized
- The app will have a login page (single sign-on (SSO) will have extra points if implemented)—there should be a DB (e.g., PostgreSQL) handling encrypted user data.
- Users will be able to create& delete the profile and upload photos. 
- The app will allow users to connect their profiles to Instagram and other social media. 
- The app will collect information about a user, including parameters related to the type of adventure, skill level, preferences, and attitude. Examples of adventures include skiing, backpacking, travel, hiking, holidate, etc. 
- The app will have a matching engine/algorithm to effectively match users (through a swipe left and swipe right type of mechanism) with others that suit their adventure, skills, and behaviors within their selected mile range. 
- The app will have in-app messaging to enable users to coordinate an outing or simply chat with a match (voice note, audio, and video call options will have extra points if implemented).

### Architectural Overview
[Create a diagram for this at some point?]

Technologies used:
- Project is primarily written in Kotlin, utilizing Sprint Boot to run the service.
- Figma was used to mock up the UI design for the web application
- The Single Sign On (SSO) login is implemented via OAuth2
- Deployments are managed via GitHub Actions
- Project is hosted on (TBD)

### Project Kanban Board
The Kanban Board for this repository is publicly hosted via GitHub Projects can be found [here](https://github.com/users/jtfoster2/projects/1/views/1)

This Kanban Board is primarily maintained by the Product Owner, Koko Afantchao. Contact them for questions regarding backlog items.

Stories can be modified only by the project contributors, but all items should be publicly viewable. If you can not access the board, contact John Foster for support.

Stories on the project board are automatically sorted by the assigned priority of the issue determined by the team during our standups or backlog meetings. 

Definition of Ready for Kanban Stories:
- Title - Stories should have a title that reflects the step or stage of the feature we are trying to fulfill.
- Opening Sentences - Issues should open with the following template "As a Developer/User/Viewer/etc , I want to ACTION so that I can RESULT"
- Additional Details - Any relevant information that will help complete the Acceptance Criteria should be listed in an "Additional Details" section in the issue description
- Acceptance Criteria - All steps to complete the issue should be listed under an "Acceptance Criteria" section in a bullet point format.
- Story Point Estimation - One story point is assumed/approximated to be one hour worth of work on the issue. A 4 point story is expected to take a quarter of the sprint to complete.

#### Tasking
Tasks for each individual story/issue will be documented as checkbox items in the descriptions for tracking and completion.
- [X] Example Checkbox for a task as it would show in a story

### Team Roster
This project was created by Team Silver Comet for Emerging Software Engineering Processes class in Summer 2024

The professor for this class was: Reza Meimandi Parizi

Student List:
- Clifton Malecki - Lead Engineer
- Koko Afantchao - Product Owner 
- John Foster - Scrum Master 
- Vladimir Maximov - UI Engineer
- Krishna Boddu - UI Engineer

### Story Point Forecasts

Sprint 1 - Initial Estimate is 16 points per person over the course of the 2-week sprint.
Sprint 2 - TBC
Sprint 3 - TBC (Will be shorter since this sprint is 1-week only)

### How to Start The Starter Project
- Assumptions:
  - You have docker installed.
  - You have java home set. (for gradlew)
- Clone this repo
- Run the build script in the scripts folder which will
  - build the project locally with gradlew
  - build the docker image
  - run the docker compose file
