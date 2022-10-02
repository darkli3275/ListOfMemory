# The Journeyman's Scrapbook

My proposed project is an application which serves as a chronicle for those
who wish to store their experiences or goals in one place. The app will be able to store
a list of user-generated elements (temporarily named "memories") each with...

- a title,
- a description,
- a location tag, 
- a date,
- and an image (removed to reduce scope of project}
- with possible expansion to include more features and more images...

and be flexible enough that all kinds of experiences can be catalogued
from the unique bird species one spots from birdwatching to a history of all the iconic
landmarks one has visited.

## The Inspiration
...for this project was my history as a **Pokemon Go**
player. The game incorporates visiting real-world points of interest into its gameplay, and
I sometimes wished I could preserve a list of all the personally notable sites I've visited on
my journeys. The game unfortunately does not support this functionality, so I decided to take
matters into my own hands by creating this as my personal project.

## User Stories
As a user, I want to be able to view the memories I created.

As a user, I want to add multiple new memories and to my memory storage.

As a user, I want to be able to remove memories from my memory storage.

As a user, I want to be able to edit the memories I created.

As a user, I want to save all the memories I have created.

As a user, I want to retrieve all the memories I have created.

# Instructions for Grader
- You can add one new memory to the existing list of memories.
Press "Load Your Memories" on the welcome screen to enter the memories screen,
then press the "+" button.
- You can add up to 9 new memories to the existing list of memories at once by following the steps above
but pressing a number on the keyboard this time.
- You can view the details of the memories on the "Welcome to your memories!" page in greater detail by clicking them.
Note that pressing "Back" on memory details page will return to the previous page but with memories displayed beginning
at the one you just clicked into.
- You can view more memories than can fit on one "Welcome to your memories!" page by clicking "Prev" or "Next".
- You can edit the information of a memory by following the point above,
clicking on an area where the memory's information is displayed,
entering new information into the text box, then pressing enter on the keyboard.
- You can delete a memory by going into its details screen and pressing the "Del" button.
- You can locate my visual component by running the application. It is the background image on the first screen.
- Save the state of the application by clicking "Save" on the "Welcome to your memories!" page.
Note that the state is not saved when "Back" is pressed on the same page.
- Load the state of the application by clicking "Load Your Memories" on the starting screen.
An empty list of empty memories will be loaded if the save file could not be found.

## Phase 4: Task 2
When starting from an empty or non-existent save file:
pressing the "+" button to add 1 memory,
then going into that memory and pressing the "Del" button to remove it,
then pressing 3 on the keyboard to add 3 memories,
then going into a memory and pressing the "Del" button to remove it,
then exiting the program will result in the following console output:

1 memory has been added  
1 memory has been removed  
1 memory has been added  
1 memory has been added  
1 memory has been added  
1 memory has been removed  

When loading a list of memories, the EventLog will also count it as adding a memory with the current state of the code.
Loading a list of 4 memories will result in the following console output:

1 memory has been added  
1 memory has been added  
1 memory has been added  
1 memory has been added  

## Phase 4: Task 3

If I had more time to work on the project... I would:

- Change Memory and MemoryEditingPage to not directly associate with Memory to reduce coupling. This would be trivial to
do as those classes' constructors already require an index number corresponding to a Memory in the ArrayList\<Memory> of
Memories. I thought using Memory fields directly was better for the purposes of the project though because those classes
don’t need to make calls to Memories and search the ArrayList\<Memory> each time they want to access a Memory.


- Add an abstract class or interface Page to the ui package and have WelcomePage, MemoriesPage, MemoryPage, and
MemoryEditingPage extend/implement it to reduce repetitive code such as: 


    private JFrame frame;
    private JLayeredPane pane;




