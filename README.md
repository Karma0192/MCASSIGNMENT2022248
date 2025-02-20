# MCASSIGNMENT2022248

# compose
A sample Android application built using Jetpack Compose. 
The app lets users select a source and destination from a fixed list, computes the route, and displays journey details including the route table, 
progress information, and unit conversion between kilometers and miles.
Dataset is fixed right now:  **Japan**, **Korea**, **China**, **India**, and **Singapore**.

**Route Table:** 
  - Displays each leg of the journey in a table with columns for the route (e.g., "Japan → Korea") and its distance.
- **Journey Log:** 
  - Shows the current location, whether a visa is required for that location, the distance covered, and the remaining distance.
- **Progress Bar:** Visualizes the journey progress.
- **Unit Conversion:** 
  - Includes a toggle button that converts distances between kilometers and miles.
- **Navigation:** 
  - A "Next Stop Reached" button updates the current position along the route.

How to Use:

Select source and destination from dropdowns.
View the computed route table with legs and distances.
Check the log for current location, visa info, distance covered/left.
Toggle units (km/miles) with the button.
Tap "Next Stop Reached" to progress the journey.

# xml
This Android application simulates a flight journey using traditional Android Views. It consists of two main screens:
**Main Screen:**  
   Enter the source and destination in text fields.
   Tap the **Start Journey** button to move to the journey details screen.
**Journey Details Screen:**  
   - Displays journey details such as current stop, visa requirements, and distance to the next stop.
   - Shows a progress bar indicating journey progress.
   - Provides a **Switch Units** button to toggle between kilometers and miles.
   - Includes a **Next Stop Reached** button to progress through the stops.

- **Progress Tracking:**  
  Calculates the total journey distance, the distance covered, and the remaining distance. A progress bar visualizes the journey progress.

- **Unit Conversion:**  
  A button toggles the distance display between kilometers and miles.






