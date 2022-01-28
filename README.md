# prime-founder

While choosing the algorithm to find the number of primes in a given range, I found 3 Sieve algorithms:
Sieve of Eratosthenes which is one of the oldest and easiest methods for finding primes in a given range which has a time complexity of O(nlog(log(n)));
Sieve of Sundaram follows the same operation of crodssing out the composite numbers as the sieve of Eratosthenes and has the time complexity of O(nlog(n));
Fastest algorithm I found was the Sieve of Atkin. It creates a sieve of prime numbers smaller than 60 except for 2, 3, 5. After that, it divides the sieve into 3 subsets, and using each subset, it marks off the numbers that are in fact solutions to some quadratic equation andthat have the same modulo-sixty remainder as the subset it uses. In the end the multiples of square numbers are eliminated and 2, 3, 5 along with the remaining ones are returned. The result is the set of prime numbers smaller than n. It's time complexity is O(n) which looks better than other 2 algorithms and that is the algorithm I used for solving this problem.
