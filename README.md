UTA Mobile Vending System.  
This system is used to manage mobile food vending locations, contents and sell products to users.

Vehicle search. The vending user shall be able to list vehicle locations showing the vehicle name, type, and assigned location (location id).
Vehicle Inventory. The system shall show the vending user the results of the inventory search as follows (the user shall input a vending vehicle name:
    1. Number of drinks remaining
    2. Number of sandwiches remaining
    3. Number of snacks remaining
    4. Currently assigned duration ending time
    5. Currently assigned location
The Vending manager/operator will be able to see additional details.
    6. Total revenue today
    7. Next assigned location today
    8. Name of operator (Vending manager only)
Three different kinds of users for the system:
    1. Vending manager 
        a. creates own profile
        b. performs a vehicle search
        c. views a vehicle inventory (current day only)
        d. determines available vending vehicle for next day (returns a search of all vending vehicles that are available the next day)
        e. views details of a specific vehicle operator
        f. assigns a location to a vending vehicle for the next day
        g. assigns an operator to a vending vehicle (assigned the entire day)
        h. updates his own profile
    2. Vending user (student, faculty, staff, non-student)
        a. creates own profile
        b. performs a vehicle search
        c. views inventory of a specific vehicle
        d. purchases item(s) (item type, quantity and cost are given) - this can be performed both remotely and at the vehicle
        e. updates his own profile
    3. Vending Operator
        a. creates own profile
        b. views inventory of his vehicle
        c. views today's schedule and location
        d. updates his own profile
The vending manager assigns all available vending vehicles late afternoon for the next day by location. Each location has an assigned slot starting at 8am with the next available slot determined by the duration for that location. The vending manager, not the software, makes sure not to overbook a vending vehicle and/or operator.
All users will have the standard functions like register, login, logout. Each system user must register and for simplicity selects their role during registration. Assume that System users do not have to be UTA students.

This project uses SQLite and a local database, which was made just for learning Android and Software Development Life Cycle.