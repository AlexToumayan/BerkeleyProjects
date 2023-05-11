# Project 3 Prep

**For tessellating pluses, one of the hardest parts is figuring out where to place each plus/how to easily place plus on screen in an algorithmic way.
If you did not implement tesselation, please try to come up with an algorithmic approach to place pluses on the screen. This means a strategy, pseudocode, or even actual code! 
Consider the `HexWorld` implementation provided near the end of the lab (within the lab video). Note that `HexWorld` was the lab assignment from a previous semester (in which students drew hexagons instead of pluses). 
How did your proposed implementation/algorithm differ from the given one (aside from obviously hexagons versus pluses) ? What lessons can be learned from it?**

Answer: my randomness algorith was a little bit different, instead of iterating through the entire method body for 11 lines, i found using our math method we could find randomness placement in a easier way. Educative.io provided a great templete on ath methods ability to understand and place randomness using only 2 instances. i found it more unique to me. because as an individual i find it extremely difficult to write huge lnes of code because i make a large majority of problems. 

-----

**Can you think of an analogy between the process of tessellating pluses and randomly generating a world using rooms and hallways?
What is the plus and what is the tesselation on the Project 3 side?**

Answer: When looking at a room one must ask them selves what constitutes a room? For me that answer was 4 corners. I must look at an origin point and see how i can find corners from naviagtng from that randomly generated origin. In the case of a cross its just 1 origin point and 4 extra points rather than 3. When generating them our basic method is simple. Crosses however are far easier to build and create tessilations in. Because there is less to think about, hallways have been the most difficult aspect to implement for BYOW because bugs are prone to be developed. For example, in a cross we check the corners, build our paths to each corner. and then make sure no point is inside that cross. the mechanism is simple to implement. However, with hallways i have to make sure theyre adjacent to our randomly generated room. I need them to connect so i cant state that those points created are inside the points of my room. But if i dont have that method to check if a point is inside then ill have rooms build ontop of one another. Thus creating a multitude of problems. 

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating pluses.**

Answer: initially what i would do is start a pointer class that would set some random "point" from that point i would generate some random position from the X,Y created and call that "origin". Next i would build points in the top left bottom right and top right using the geomtry of coordantes. from the coordiantes i would then build verticle and horizontal walls. From the walls I would then check if a point from another room is inside my room. 

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: a hallway and a room have 4 corners, however the difference is the fact one has 4 walls the other only has 2. 
