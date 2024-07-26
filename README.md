<strong> **DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

# WESTERN GOVERNOR UNIVERSITY
## D424 SOFTWARE ENGINEERING CAPSTONE
### Travel Planning App

This travel planning app is a tool to help create, manage and organize your travel itineraries as well as any associated activities and vehicle rentals. Alerts can be set to act as reminders for travel itinerary start and end dates, as well as for all activity dates. All travel itineraries can be shared with friends, family, or colleagues.


## BASIC INSTRUCTIONS

> ## 1. USER LOGIN
>### The initial UI you will encounter upon launching the Travel Planner is the User Login screen.
> Enter Username & Password to login.
> - Usernames must not contain any special characters or empty spaces.
> - Passwords must be at least 8 characters long and contain at least one number.
> - Once username and password are entered, select the " Sign In" button.
> - If your username or password do not meet the requirements, an applicable error message will appear.


> ## 2. HOME
> ### The home screen will load after you click the "Sign In" button.
> Select User Type and click the "Plan Your Travel!" button.
> - Selecting User Type is not a requirement for this version.
> - Future versions will have additional functionality based on user type.


> ## 3. SAVED TRAVEL ITINERARIES
> ### The saved travel itineraries screen will load after you click the "Plan Your Travel" button.
> - On your first visit to this page or if you have not previously saved any vacations, no items will be listed.
> - New itinerary details can be added by selecting the "+" floating action map pin button.
> - Any previously saved vacations will be listed here and will include destination, start, and end dates. 
> - Select the desired itinerary to view, edit, or delete.
> - Log Data can be viewed by selecting the three vertical dots in the top right hand corner and selecting "View Logs".
> - Sample vacation data can be loaded by selecting the three vertical dots in the top right hand corner and selecting "Add Sample Data".
> - The vacations screen will need to be refreshed for the sample data to appear.


> ## 4. ITINERARY DETAILS
> ### The Itinerary Details screen will load after you click the "+" floating action map pin button.
> - Enter desired travel destination information
> - Enter desired hotel name information
> - Select the start date for the itinerary by clicking the "Select Start Date" button.
> - Select the end date for the itinerary by clicking the "Select End Date" button.
> - Selecting the three vertical dots in the top right hand corner will provide multiple options:
> > - Save Itinerary
> > - Delete Itinerary
> > - Alert Start
> > - Alert End
> > - Share
> > - Note: Itineraries must first be saved before alerts or sharing can be utilized.
> > - Appropriate error messages will appear if you attempt to save an itinerary without completed Destination & Hotel information, or Start & End Dates.
> > - Appropriate error messages will appear if you attempt to set an alert or share an itinerary that has not been saved.
> - After saving an itinerary, you will be returned to the Saved Travel Itineraries screen. There you will be able to select your saved itinerary, add activities, car rental, alerts, and share or delete. 
> > - New activity details and car rentals can be added by selecting the "+" floating action button.
> > - The floating action button will be located at the bottom right hand corner of the screen, however it is disabled by default until an itinerary is saved. Once saved, it becomes accessible.
> > - Clocking the floating action button will load two additional floating action buttons. 
> > - The star icon fab is for adding activities
> > - The car icon fab is for adding car rentals.
> - Additional vacations can be saved by repeating the above steps.


> ## 5. ACTIVITY DETAILS
> ### From the Itinerary Details screen, the Activity Details screen will load after you click the star icon floating action button.
> - Enter desired activity information
> - Select the start date for the activity by clicking the "Select Activity Date" button.
> - Selecting the three vertical dots in the top right hand corner will provide multiple options:
> > - Save Activity
> > - Delete Activity
> > - Set Alert
> > - NOTE: Activity must be saved prior to setting an alert
> > - All activities must be deleted before the itinerary can be deleted.
> > - Appropriate error messages will appear if you attempt to save an activity without completed Activity information, or Activity Date.
> - Additional activities can be saved by repeating the above steps.
> - Saved activities will appear in the Vacation Details screen. Select the desired activity to make any changes, save, delete, or set an alert.


> ## 6. CAR RENTAL DETAILS
> ### From the Itinerary Details screen, the Car Rental Details screen will load after you click the car icon floating action button.
> - Using the dropdown menu, select the desired car rental.
> - Selecting the three vertical dots in the top right hand corner and clicking "Save Rental" will save your car rental selection and add it to your itinerary.
> - Saved car rentals will appear in the Itinerary Details screen. Select the saved car rental to make any changes.


> ## 7. LOG DETAILS
> ### From the Saved Travel Itineraries screen, the Log Details screen will load by selecting the three vertical dots in the top right hand corner and selecting "View Logs".
> - Logs contain date and time stamps as well as tags for various activities.
> - Enter desired search criteria in the search bar and click "Search" button to filter logs.
> > - EXAMPLE: A search of the tag "CarDetails" will return all logs that have been tagged with "CarDetails".
> - Select back arrow to return to Home screen.


## SUPPLEMENTAL DETAILS

The working branch git repository is located at: https://gitlab.com/wgu-gitlab-environment/student-repos/vmar387/d424-software-engineering-capstone.git
The app is hosted for invited test users at https://play.google.com/apps/internaltest/4700230095996245168
Video demonstration of the app is located at: https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=4e476963-dcc8-4484-90ed-b1b801434994#
