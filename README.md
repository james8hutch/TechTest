# TechTest

Initial draft only contains provided java file, and some JUnit tests that we should get working

First actual PR contains Cube, Sphere, and Tetrahedron classes requested plus JUnit tests to verify validity under requested conditions.

I chose to implement an interface for these classes to ensure a consistent design.

Finally I chose to implement a small TestUtils class to ensure consistent testing.

Second PR uses synchronized to solve the first problem.
I have also created another PR to demonstrate use of AtomicInteger to also solve the problem.

Timings of problem1 test
What I've done here is run the problem1 test 5 times in IntelliJ
Synchronized: 215, 223, 240, 249, 241
AtomicInteger: 450, 479, 507, 450, 441

