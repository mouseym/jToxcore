TODO
========

## Core functionality ##
- Loading and saving

## API Redesign ##

We could wrap the friendnumber, status, statusmessage and such in a dedicated ToxFriend.java class, and pass 
an instance of that to each method where the friendnumber is required. This would also allow for some easier 
encapsulation of data for API users, as it gives them a central place to store data for each friend. The 
friends should be pure data objects with only setters and getters, all methods should remain in the core 
class.

### Required fields: ###

- Name (internal tox name of this friend)
- Nickname (local name to override the friend's name)
- ID (the human readable hex-ID)
- friendnumber (the internal number of this friend)
- status (One of ToxStatus)
- statusmessage (the user's status message
- sendreceipts (whether or not we are sending receipts to this user)

### Required methods ###

- getters and setters
- should NOT contain any logic, only a data container

### General class ###
The Friend class should carry a notice that the core is the ultimate authority for these things, and that 
data in 
the Friend object might be incorrect, especially after manually using a setter ("Here be dragons")


### Required changes to the JTox class ###
Each method that takes a friendnumber as a parameter should instead take a Friend instance instead. If the 
method is a "getting" method, update the Friend instance with the received data after executing the native 
call. If the method is a "setting" method, update the Friend instance with the data we sent after we know the 
call succeeded (no exception was thrown).

To keep everything in sync, we should add a wrapper class (ToxData, if anyone has a better name, use it 
instead)  that contains a ReentrantLock and a List<Friend>. The validPointers Map should then have the 
following Type signature: Map<Long, ToxData>. This way, it is easy to keep track of our friends and close 
down our instance once we are offline.

isValidPointer behaviour needs to be changed to reflect this, as well as the JTox constructor

### Data class ###

#### Fields ####

- ReentrantLock
- List<Friend>

#### Methods ####
- Getters, no setters necessary (Object should be immutable, while the List<Friends> is indeed mutable)
