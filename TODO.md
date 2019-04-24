## Initial Setup
- [x] Create database file  
- [x] Implement required methods  

## Setup tables
- [ ] Figure out SQL syntax needed to initiate tables  
- [ ] Write insertion text for putting initial data into tables  

- [ ] Determine what insert methods we need (DBHelper)
    * private void updateMyDatabase((SQLiteDatabase db, int oldVersion, int newVersion)
    * private void addDog
    * private void addFood
    
- [ ] Write update/insert methods (DBUtilities)
    * public boolean addAllergy  
    * public boolean deleteAllergy
    * public boolean updateDogName
    * public boolean updateDogBreed 
    * public boolean updateDogWeight

- [ ] Determine what get methods we need  
- [ ] Write get methods  

## Notes
* DBHelper is the main database class. Handles creation of tables and initial data.
* DBUtilities is a way for the user to interact with the database class. Handles updating resources per user activity.
