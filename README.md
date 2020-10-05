# Kunal Farmah
# Behtaar Assignment

## Uses API: https://jsonplaceholder.typicode.com/posts/

### Uses RecyclerView to show the items.

### Uses MVVM pattern for the architecture (View, Viewmodel, Web and Room Repository). 
### Screen Orientation handled explicitly, no locked orientation, use in any orientation.
### Added layouts for landscape mode.
### Added animations on orientation changes.
### Stored the data in Room to provide offline support.
### Everytime the app is launched, api is called and any new record is inserted into the database.
### If the database is empty, then internet is required to populate it for the first time, after that the data is persistently avialable offline.
### The app senses if data connection is present and accordingly decides weather to call api or load data from database.
<p> <img hspace="10" src="https://github.com/KunalFarmah98/BehtaarAssignment/blob/master/app/src/main/res/raw/port.jpg" width =200 
  height = 350/>
 <img hspace="10" src="https://github.com/KunalFarmah98/BehtaarAssignment/blob/master/app/src/main/res/raw/land.jpg" width =350 
  height = 200/>
   <img hspace="10" src="https://github.com/KunalFarmah98/BehtaarAssignment/blob/master/app/src/main/res/raw/nodata.jpg" width =350 
  height = 200/></p>
