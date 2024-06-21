# Project Vision
Empowering passionate outdoor enthusiasts seeking compatible partners for unforgettable experiences . Outdora is web-based dating app that focuses on shared adventures matching. Unlike other dating apps,OutDora vision to connect adventure seekers and build thriving community.

 ### Near vision 
- **Core Functionality:** Launch a user-friendly web app with robust matching algorithms based on outdoor activity preferences, skill levels, and location, with  one to one  and group messaging  feature,discussion forums, event calendars for local hikes/climbs
- **Critical Mass:** Onboard a minimum of  100K active users who regularly update profiles and engage with the app.
-  **Safety Features:** Implement robust safety features like emergency contact sharing, in-app messaging with location tracking (optional), and comprehensive user verification.

### Long therm  Goals 
 - **Matching Algorithm:** Develop a robust matching algorithm that goes beyond basic profiles. Integrate adventure preferences, past experiences, desired activity levels, and risk tolerance.
 - **Partnerships:** Establish partnerships with outdoor gear companies, event organizers, or national parks to offer exclusive benefits to Outdora users


 ## Loging Feature:
  The app will have a login page (single sign-on (SSO) will have extra points if implemented)there should be a DB (e.g., PostgreSQL) handling encrypted user data.
    
### User stories 
-As a user, I want to securely log in to the app using single sign-on (SSO) whenever possible. If SSO is unavailable, I want to be able to log in with a username and password. The app should encrypt my login credentials and store them securely in the database.
    #### Acceptance Criteria
    -Login page should clearly show the availability of SSO login
    -SSO login should be implemented using a popular service (e.g., Google, Facebook)
    - Upon  successfull SSO login, redirecte the user to outdora home page
    -If SSO login failed, redirect user to login page and clealy indicate the pissibility to login with user name and password 
    -When user login with user name and password, user should confirm the email by click on link send to his email , to successfuly complete the login

-As a sign up user with SSO , I want to be autimaticaly authenticated after launching the app. This will allow me to access the app quickly and securely without having to log in repeatedly.

    #### Acceptance Criteria 
    - when appp is luanch and user SSO session is still valide automaticaly authenciate the user 
    -if SSO session is not valide, redirect user to login page and force user to login again before access ressources 
    -

- As a sign up  user, I want to set up password for my account, so I will also have the possiblity to login with password and email when SSO is not available.
     #### Acceptance Criteria
     - After SSO login, user should be able to set up password for his account
     - During password creation the user is required to enter the password twice for confirmattio 
     - The password complexuty should be enforce by the system (e.g.,
     minimum length, character types)
     - After password is created, the system send email with link to the user email address provider to confirm his email to enable login with email& passward
     -


- As a registered user I want to reset my password, if I forget it  or after three unsuccessful login attempt so I can access again my account 

   ##### Acceptance Criteria 
   - The App loging page should provide a "Forgot Password" (reset password link) on the login page and also in the user profil setting 
   -when valid email address is enter and the system valide the email against registered accouts, the system send email to with a link to reset the password. 
   -  Upon successful password reset, the user receives a confirmation email.


-  As user I want to use two factors authentication method every time am login in to my account To make sure I'am the only one logging into my account
 
 #### Acceptance Criteria
   - A clear option is provided to the user to enable two-factor authentication
   - User will option for various 2FA methods  (SMS, email phone call )
   - Upon succesfull 2FA verification , the user is granted full access to thier account
  


## User Profil management with photo upload Feature 
 Users will be able to create& delete the profile and upload photos.
 
 ### Functionality:

Profile Creation: Users will have the ability to create a new profile within the app. This may involve providing basic information such as name, email address, and potentially optional details like location or a short bio.

Profile Editing: Users can edit their existing profiles at any time. This allows them to update their information or tailor their profile details as needed.

Profile Deletion: Users will have the option to completely delete their profiles from the app. This should be a deliberate action, potentially requiring confirmation to ensure users don't accidentally erase their profiles.

Photo Upload: Users can personalize their profiles further by uploading a profile picture. The app should define clear guidelines for photo size, format, and content restrictions (e.g., no offensive content).

Data Privacy: User profile information, including uploaded photos, will be stored securely within the app's database. Users should have control over their data and may have the option to choose who can see their profile information.

## User stories  
- As new user I want create a profile with basic information such as (Name, Age .... )So that i can have a personalize profile and interact (match) with other user 
  #### Acceptance criteria  
  - The app should provide a clear and easy-to-use interface for creating a new profile
  - User can optionally add basic information and also additional profile details beyond basic (Bio, location , Interests,Hobbies  ) 
  - User profil information is displayed on thier profil page. 


- As a user I want to edit my profile information so I can keep my profile up to date 
   #### Acceptance criteria 
  - In the user account setting user profile information is displayed in a clear editable format.
  - User can't modify certain information such ass (age )
    clearly indicate the user that age can only modify once.
  - updated profile information should reflected on the user profile and other area of the platform  





- As a  registered user, I want to upload more than one  profile photo so that I can personalize my profile and, make it more visually appealing to other users.
   #### Acceptance criteria
   - The app should allowed the user to upload up to 10 pictures 
   -  system will valide the upload picture size and formate
   -  Display the photo preview to user after photo is upload 
   -  Give the option to user to arrange the picture by preference
   -

- As registered  use who no longer want to use the app i should be able to permanently delete my profile and all my personal data   
    #### Acceptance criteria
  - User account Setting should provide a clear option  for profile and data deletion 
  - User should be prompted  and with password field to confirm the deletion of their profile and data  
  - After deletion user should not be able to access their profile and data
  - Confirmation email is send to the user with link to download personal data  after successful deletion



- As AI supervision tools , I want to be able to review user-uploaded photos and remove inappropriate content so that I can maintain a safe and positive user experience.
#### Acceptance 
 - The AI tool can identify inappropriate content with a high degree of accuracy (e.g., above 90% precision and recall). 
 - The AI tool operates efficiently to minimize delays in content review and user experience.dd


## Connecting Profil to Social media Account feature 
  The app will allow users to connect their profiles to Instagram and other social media.
   
   ## User Stories
   - As register user, I want to connect my profile to other social media plateform during profil creation or after, so That I can rapidly establish social netwok within the app
   
       #### Acceptance criteria
       - The user profile information page will provides an oprion to connect to socail media accounts. 
       - Use after selection of prefere social media , the app guides the user through the secure OAuth authorization process
   
   - As a registered user, I want to be able to manage and disconnect my connected social media accounts within the app settings.
        
        #### Acceptance Criteria 
         * The user profile settings section displays a list of currently connected social media accounts.
         * User can disconnect any connected social media account with a single click.
       

## User Stories for Adventure Profile Creation feature 
   The app will collect information about a user, including parameters related to the type of adventure, skill level, preferences, and attitude. Examples of adventures include skiing, backpacking, travel, hiking, holidate, etc. 

   ## User stories
  - As a new user, I want to create a profile that specifies the types of adventures I enjoy (e.g., skiing, backpacking) so that the app can recommend suitable adventures and connect me with like-minded users.
  #### Acceptance Criteria 
       * User profil edite should include a sections to select their preferred advanture types. 
       * User can select multiple adventure types from a predefined list.
        

  - As a registered user, I want to indicate my skill level for different adventure types (e.g., beginner skier, advanced backpacker) so that I can find adventures and connect with users who match my experience.
  #### Acceptance Criteria
      * The user profile settings provide options to specify skill levels for different
      * User can select from predefined skill levels (e.g., beginner, intermediate, advanced) for
      * Users can update their skill levels as their experience grows.


- As a registered user, I want to refine my adventure preferences beyond just types and skill level (e.g., budget, travel style) to ensure the app recommends adventures that truly suit my interests.
#### Acceptance Criteria
   * The user profile settings offer additional options to specify preferences beyond adventure types and skill levels
   * Users can indicate their preferences for specific aspects of the adventure (e.g., cultural immersion, relaxation, physical challenge).


## Finding adventure aatches feature 
The app will have a matching engine/algorithm to effectively match users (through a swipe left and swipe right type of mechanism) with others that suit their adventure, skills, and behaviors within their selected mile range.
## User Stories 
  - As a user, I want to find a match who share my interests in adventure activities  so that I can connect with and plan exciting outings together.
  #### Acceptance Criteria
       * The matching engine considers the user's chosen adventure activities when recommending potential matches.
       * Users can see a list of recommended matches with their preferred adventure activities highlighted.
       * The user can set their desired search radius (in miles) to find compatible matches within a specific geographic location.

  -  As an adventurer, I want to learn more about potential partners before swiping left or right to make a more informed decision. 
  #### Acceptance Criteria
       * The app provides a brief summary of each potential match, including their adventure preferences, skill levels
       * User profiles can include additional information beyond the core matching criteria (e.g., bio, photos, past adventure experiences).
       * Users can view this additional information about potential partners before swiping.


## In App messaging Feature 
The app will have in-app messaging to enable users to coordinate an outing or simply chat with a match (voice note, audio, and video call options will have extra points if implemented).

## User Stories
- As a user, I want to communicate with my matches through in-app messaging so that we can get to know each other.
  #### Acceptance Criteria
  * I can send text messages to my matches within the app.
  * I can see a list of all ongoing message threads with my matches.
  * I can see the date and time of the last message in each thread.
  * I can send meme and emoji within the chat to enhance the conversation. (Bonus)
