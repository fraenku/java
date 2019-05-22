#Java 7

This pages describes the new features in Java 7

##Threads
###Fork/Join Framework
The ideas of the fork/join framework are coming from divide-and-conquer algorithm.
A task is recursively forked into smaller subtasks until each subtask is small enough.
Then the subtasks are evaluated in parallel and joined together.

See ForkJoinSumCalculator.java for an example. 