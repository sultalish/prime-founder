import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

class Solve
{
	// Using Semaphore as a lock
	public final static Semaphore lock = new Semaphore(1, true);

	static long total_num_primes = 0;
	static long total_sum_primes = 0;
	static int max_val = 100000000;
	static int num_threads = 8;

	public static boolean primes[] = new boolean[max_val + 1];

	// Threads
	private List<Sieve> primeCalculators;
	private List<Thread> primeCalculatorsThreads;

	public static void main(String args[])
	{
		// START COUNTING HERE
		long timer_start = System.currentTimeMillis();

		Solve main_thread = new Solve();

		// Create threads to calculate primes
		main_thread.primeCalculators = new ArrayList<>();
		main_thread.primeCalculatorsThreads = new ArrayList<>();

		for (int i = 0; i < num_threads; i++)
		{
			Sieve prime_calculator = new Sieve(main_thread, i);
			main_thread.primeCalculators.add(prime_calculator);
			Thread th = new Thread(prime_calculator);
			main_thread.primeCalculatorsThreads.add(th);
		}

		for (Thread thread : main_thread.primeCalculatorsThreads)
		{
			thread.start();
		}

		// Now waiting for threads to finish
		for (Thread thread : main_thread.primeCalculatorsThreads)
		{
			try
			{
				lock.acquire();
				thread.join();
				lock.release();
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}

		// END COUNTING HERE
		long timer_end = System.currentTimeMillis();

		// Terminate all threads
		for (Thread thread : main_thread.primeCalculatorsThreads)
		{
			thread.interrupt();
		}

		for (int i = 2; i<= main_thread.max_val; i++)
		{
			if (primes[i] == true)
			{
				main_thread.total_num_primes++;
				main_thread.total_sum_primes += i;
			}
		}

		long execution_time = timer_end - timer_start;

    // Count top ten primes
		int counter_top_ten = 0;
		int[] top_ten_primes = new int[10];
		for (int i = max_val - 1; i > 0 && counter_top_ten < 10; i--)
		{
			if (primes[i] == true)
			{
				top_ten_primes[counter_top_ten++] = i;
			}
		}

    // Write all data to primes.txt file
    try
    {
        // Create a new output file
        new File("output").mkdirs();
        File file = new File("output/primes.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter("output/primes.txt");

        // Print data to first line
        fileWriter.write(execution_time + "ms " + total_num_primes + " " + total_sum_primes + "\n");

        for (int i = 9; i >= 0; i--)
        {
            // Print the values backwards so they are from smallest to largest.
            fileWriter.write(top_ten_primes[i] + " ");
        }

        fileWriter.close();
    }
    catch (IOException e)
    {
        System.out.println("[IOException]: ");
        e.printStackTrace();
    }
	}
}

// SieveOfEratosthenes
class Sieve implements Runnable
{
	private Solve main_thread;
	private int thread_num;

	public Sieve(Solve main_thread, int thread_num)
	{
		this.main_thread = main_thread;
		this.thread_num = thread_num;
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try {
			sieve(thread_num);
		} catch (Exception e)
		{
			System.out.println(e);
		}

	}

	void sieve(int thread_num)
	{
		for (int i = 0; i <= main_thread.max_val; i++)
		{
			main_thread.primes[i] = true;
		}


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
	}
}
