Coworking space applications:

Class Admin:
Added for regulating workspaces(adding, removing, viewing) and for viewing all the reservations made by user. In viewAllReservations method attempted to use sreams instead of for loop

Class User:
The main functionality is adding and removing reservations made by customer. For every object there is a list of reservations. 

Class UsersBase:
This class allows users to avoid data loss. This class was made to fix a problem with logining. If user logins for the first time, enters his name and makes a reservation, than exits from the user menu to main menu
he won't be able to log in again and view his reservations. UsersBase fixes this problem by storing all the usernames, and if the user tries to log in again, his username will be found and his data will be saved.

Class Reservation:
Standart getters and setters for creating a reservation.

Class Workspace:
Stores all the reservations connected to a workspace. This was made for viewing available dates mechanism. Stores price, id and name as well

Class ApllicationStateManager:
This class was made to store application state in a save.dat file. It store users base and workspaces. This allows user to save his reservations after exiting the program. This was made by serialization.

Class TextDataLoader:
Reads workspaces from a file. The data in a file must be written in a format id|name|price. Space is not a separator because some workspaces can be named with double words(private room, public room for example)

Class CustomClassLoader:
Just a custom class loader

