# Lukas Chatbot User Guide

The following instructions will help guide you through the basic functions of the Lukas Chatbot.

## List of Commands

1. **list**: Prints out all task added to current list.  
   Format: `list`
2. **todo**: Adds a task to the list  
   _Feature_: Requires a task only. No time limit required.  
   Format: `todo <task>`
3. **deadline**: Adds a task to the list that should be completed by a certain date.  
   _Feature_: Requires a task and a time.  
   Format: `deadline <task> /by <time>`
    1. time should follow ***yyyy-mm-dd HHmm*** `e.g. 2019-12-02 1800`
4. **event**: Adds a task to the list that should be completed within a certain duration  
   _Feature_: Requires a task, followed by 2 timings.  
   Format: `event <task> /from <start> /to <end>`
    1. ***start*** and ***end*** should follow ***yyyy-mm-dd HHmm*** `e.g. 2019-12-02 1800`
5. **mark/unmark**: Marks/Unmarks a task on the list. Used to denote what task are completed and not completed.  
   _Feature_: Uses an integer number to denote index.  
   Format: `mark/unmark <index>`
    1. index should be a positive number
    2. index starts from 1
    3. index cannot be larger than current number of tasks in list
6. **delete**: Deletes a task from the list.  
   _Feature_: Uses an integer number to denote index.  
   Format: `delete <index>`
    1. index should be a positive number
    2. index starts from 1
    3. index cannot be larger than current number of tasks in list
7. **find**: Returns list of tasks that matches keywords used.  
   _Feature_: Uses a keyword to find matching tasks.  
   Format: `find <keyword>`
8. **bye**: Exits the program.  
   Format: `bye`

### Notes ###

Upon exiting the program, the list of task created will be saved in .txt format within the `data` file. 

