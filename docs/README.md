# BERT (Bot for Echo, Response and Talk) User Guide
![BERT](./assets/bert.png)

BERT(Bot for Echo, Response and Talk) is a task tracking chatbot, 
being uses in Command Line Interface(CLI).

# Quick Start
1. Ensure you have Java 17 or above installed in your Computer.
2. Download the latest `.jar` file [here](https://github.com/gbinw128/ip/releases/tag/A-Jar).
3. Copy the `.jar` file to the folder you want to use as the home folder for 
BERT(Preferably an empty folder).
4. Open a command terminal, `cd` into the folder the `.jar` is in and run
`java -jar BERT.jar` command to run the chatbot.
5. Type in the commands and press Enter to execute it e.g. typing `bye` and 
pressing enter will exit the program.
6. Refer to the [Features](#features) below for details of each command.

# Features

###
## List tasks: `list`
<details>
<summary>Shows the list of all recorded tasks.</summary>

Format: `list`

Example:

![list](./assets/list_example.png)
</details>


###
## Finding task: `find`
<details>
<summary>Find all tasks whose item contains the given keyword.</summary>

Format:`find <keyword>`

Example:

![find](./assets/find_example.png)
</details>

###
## Marking a task as done: `mark`
<details>
<summary>Marks a task as done.</summary>

Format: `mark <item number>`

Example:

![mark](./assets/mark_example.png)
</details>

###
## Unmarking a task as done: `unmark`
<details>
<summary>Unmarks a task as done.</summary>

Format: `unmark <item number>`

Example:

![unmark](./assets/unmark_example.png)
</details>

###
## Adding a Todo-type task: `todo`
<details>
<summary>Adds a task that is need to be done.</summary>

Format: `todo <item>`

Example:

![todo](./assets/todo_example.png)
</details>

###
## Adding a Deadline-type task: `deadline`
<details>
<summary>Adds a task that is need to be done by a certain date/time.</summary>

Format: `deadline <item> /by <date(YYYY-MM-DD HHMM)>`

Example:

![deadline](./assets/deadline_example.png)
</details>

###
## Adding a Event-type task: `event`
<details>
<summary>Adds a task that has a starting and ending date/time.</summary>

Format: `event <item> /from <date(YYYY-MM-DD HHMM)> /to <date(YYYY-MM-DD HHMM)>`

Example:

![event](./assets/event_example.png)
</details>

###
## Exiting program `bye`
<details>
<summary>Exits the program.</summary>

Format: `bye`
</details>


#
## Saving data
Task data in BERT are saved in the hard disk manually in a text file
only when exiting the program, through the `bye` command.

# Command Summary

| Action   | Second Header                                                                                                 |
|----------|---------------------------------------------------------------------------------------------------------------|
| list     | `list`                                                                                                        |
| todo     | `todo <item>`<br/> e.g. `todo study`                                                                          |
| deadline | `deadline <item> /by <date>`<br/> e.g. `deadline return book /by 2025-10-20 1600`                             |
| event    | `event <item> /from <date> /to <date>`<br/> e.g. `event game event /from 2025-10-05 1000 /to 2025-10-22 1800` |
| mark     | `mark <number>`<br/>e.g. `mark 1`                                                                             |
| unmark   | `unmark <number>`<br/>e.g. `unmark 4`                                                                         |
| find     | `find <keyword>`<br/>e.g.`find book`                                                                          |
| bye      | `bye`                                                                                                         |