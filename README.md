# Multi-Threaded Sieve of Eratosthenes Algorithm

## Problem (100 points)
Your non-technical manager assigns you the task to find all primes between 1 and 108.  The assumption is that your company is going to use a parallel machine that supports eight concurrent threads. Thus, in your design you should plan to spawn 8 threads that will perform the necessary computation. Your boss does not have a strong technical background but she is a reasonable person. Therefore, she expects to see that the work is distributed such that the computational execution time is approximately equivalent among the threads. Finally, you need to provide a brief summary of your approach and an informal statement reasoning about the correctness and efficiency of your design. Provide a summary of the experimental evaluation of your approach. Remember, that your company cannot afford a supercomputer and rents a machine by the minute, so the longer your program takes, the more it costs. Feel free to use any programming language of your choice that supports multi-threading as long as you provide a ReadMe file with instructions for your manager explaining how to compile and run your program from the command prompt.  

## Proof of Correctness,
While choosing the algorithm to find the number of primes in a given range, I found 3 Sieve algorithms:
* Sieve of Eratosthenes which is one of the oldest and easiest methods for finding primes in a given range which has a time complexity of O(nlog(log(n)))
* Sieve of Sundaram follows the same operation of crodssing out the composite numbers as the sieve of Eratosthenes and has the time complexity of O(nlog(n))
* Fastest algorithm I found was the Sieve of Atkin. It creates a sieve of prime numbers smaller than 60 except for 2, 3, 5. After that, it divides the sieve into 3 subsets, and using each subset, it marks off the numbers that are in fact solutions to some quadratic equation andthat have the same modulo-sixty remainder as the subset it uses. In the end the multiples of square numbers are eliminated and 2, 3, 5 along with the remaining ones are returned. The result is the set of prime numbers smaller than n. It's time complexity is O(n)


Firstly, I have tried to implement the Sieve of Atkin and the execution time was 300ms. When I tried to parallelize this algorithm the runtime went up to 2400ms. The problem was that my program had 8 threads that just did the same thing 8 times. I realized this but then couldn't find a way to divide the program into 8 parts. Right after that I tried to implement Sieve of Eratosthenes and my runtime was 1200-1500ms. I implemented multithreading by running 1 thread on 1 number in the following loop, so that 8 numbers could loop and do computations and modifications at the same time (where thread_num is the number of current thread and main_thread.num_threads is the max number of threads):
```java
for (int p = 2 + thread_num; p * p <= main_thread.max_val; p += main_thread.num_threads)
		{
			if (main_thread.primes[p] == true)
			{
				for (int i = p * p; i <= main_thread.max_val; i+= p)
				{
					main_thread.primes[i] = false;
				}
			}
		}
```

I've checked the corrections of the output by compiling sequential Sieve of Eratosthenes and Sieve of Atkin algorithms and by checking the following Wikipedia page: [Prime-counting function](https://en.wikipedia.org/wiki/Prime-counting_function). The results for numbers from 1 to 10^8 are:

* Number of primes: 5761455
* Sum of all primes: 279209790387276

## How to Compile and Run the Program (macOS)
1. Open a Terminal
2. Navigate to the location of the prime-founder.java
3. Compile the program:
```bash
javac prime-founder.java
```
5. Run the program:
```bash
java prime-founder
```

## Output of the program
```
<execution time>  <total number of primes found>  <sum of all primes found>
<top ten maximum primes, listed in order from lowest to highest>
```

## Efficiency and Experimental Evaluation
Processor: 2.3 GHz 8-Core Intel Core i9

* Sequential Sieve of Atkin
```
300ms
```
* Multi-Threaded Sieve of Atkin that was doing the same thing 8 times
```
2400-3000ms
```
* Sequential Sieve of Eratosthenes
```
1200-1500ms
```
* Multi-Threaded Sieve of Eratosthenes
```
400-800ms
```
