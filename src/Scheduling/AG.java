package Scheduling;

import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.Scanner;

class AG {
	private String name;
	private int arrival_time;
	private int burst_time;
	private int priority;
	private int quantum;
	private int waiting_time;
	private int comp;
	private ArrayList<Integer> fcfs_quantum = new ArrayList<>();
	private ArrayList<AG> ready_queue = new ArrayList<>();
	private ArrayList<Integer> wait = new ArrayList<>();
	private static float avg=0;
	private static float avg_TT=0;
	static private ArrayList<String> P_name = new ArrayList<>();
	static private ArrayList<Integer> P_time = new ArrayList<>();
	AG()
	{
		name = "";
		arrival_time = 0;
		burst_time = 0;
		priority = 0;
		quantum = 0;
		waiting_time = 0;
	}
	
	private AG(String s, int a, int b, int p, int q)
	{
		name = s;
		arrival_time = a;
		burst_time = b;
		priority = p;
		quantum = q;
	}
	
	private static void Print(ArrayList<AG> q)
	{
		System.out.println("Name\t" + "ArrivalTime\t" + "BurstTime\t" + "Priority\t" + "Quantum\t\t" );
		for(int i = 0; i < q.size(); i++)
			System.out.println(q.get(i).name + "\t\t\t" + q.get(i).arrival_time + "\t\t\t" + q.get(i).burst_time + "\t\t\t" + q.get(i).priority + "\t\t\t" + q.get(i).quantum + "\t\t\t" );
	}
	
	private static void Get_input(int pro_num, ArrayList<AG> queue)
	{
		Scanner input = new Scanner(System.in);
		String name;
		int arrivaltime;
		int bursttime;
		int priority;
		int qtm;
		
		for(int i = 0; i < pro_num; i++)
		{
			System.out.print("\nProcess Name : ");
			name = input.next();
			System.out.print("Process Arrival Time : ");
			arrivaltime = input.nextInt();
			System.out.print("Process Burst Time : ");
			bursttime = input.nextInt();
			System.out.print("Process Priority : ");
			priority = input.nextInt();
			System.out.print("Process Quantum : ");
			qtm = input.nextInt();
			AG obj = new AG(name, arrivaltime, bursttime, priority, qtm);
			queue.add(obj);
		}
	}
	
	
	private static void Arrival_Sort(ArrayList<AG> queue)
	{
		for(int i = 0; i < queue.size(); i++)
		{
			for(int j = i; j < queue.size(); j++)
			{
				if(queue.get(i).arrival_time > queue.get(j).arrival_time)
				{
					AG Temp = queue.get(i);
					queue.set(i, queue.get(j));
					queue.set(j, Temp);
				}
			}
		}
	}
	
	
	private static void Priority_Sort(ArrayList<AG> queue)
	{
		for(int i = 0; i < queue.size(); i++)
		{
			for(int j = i; j < queue.size(); j++)
			{
				if(queue.get(i).priority > queue.get(j).priority)
				{
					AG Temp = queue.get(i);
					queue.set(i, queue.get(j));
					queue.set(j, Temp);
				}
			}
		}
	}
	
	private static void LULU_Sort(ArrayList<AG> queue)
	{
		for(int i = 1; i < queue.size(); i++)
		{
			for(int j = i; j < queue.size(); j++)
			{
				if(queue.get(i).priority > queue.get(j).priority)
				{
					AG Temp = queue.get(i);
					queue.set(i, queue.get(j));
					queue.set(j, Temp);
				}
			}
		}
		for (int i = 0 ; i < queue.size();i++)
			P_name.add(queue.get(i).name);
	}
	
	private void Quantum_FCFS(ArrayList<AG> queue)
	{
		for(int i = 0; i < queue.size(); i++)
		{
			fcfs_quantum.add((int) ceil((float) (queue.get(i).quantum) / 4));
		}
	}
	
	private void FCFS(ArrayList<AG> queue)
	{
		Arrival_Sort(queue);
		Quantum_FCFS(queue);
		
		int TB = 0, counter = 0;
		for(int i = 0; i < queue.size(); i++)
		{
			TB += queue.get(i).burst_time;
		}
		int count = 0;
		while(counter < TB)
		{
			ArrayList<AG> FirstC = new ArrayList<>();
			for(int i = 0; i < queue.size(); i++)
			{
				if(counter >= queue.get(i).arrival_time)
				{
					FirstC.add(queue.get(i));
				}
				else
				{
					count = queue.get(i).arrival_time;
					if(count <= counter)
						counter=count;
				}
			}
			
			if(FirstC.size() > 1)
			{
				for(int i = 1; i < FirstC.size(); i++)
				{
					FirstC.get(i).waiting_time++;
				}
			}
			
			if(FirstC.size() != 0)
			{
				int index = queue.indexOf(FirstC.get(0));
				int q = fcfs_quantum.get(index);
				
				FirstC.get(0).burst_time -= q;
				P_name.add(FirstC.get(0).name);
			
				if(q > FirstC.get(0).burst_time)
				{
					counter += FirstC.get(0).burst_time;
					
					P_time.add(counter);
				}
				
				else
				{counter += q;
					P_time.add(counter);
					
				}
				
				
				if(FirstC.get(0).burst_time != 0)
				{
					ready_queue.add(queue.get(index));
					queue.remove(index);
					fcfs_quantum.remove(index);
				}
			}
			else
			{
				counter++;
			}
		}
		priorty_premativ(ready_queue);
	}
	
	private static void quantum_increased(ArrayList<AG> p)
	{
		int n = p.size();
		for(int i = 0; i < n; i++)
		{
			AG temp = p.get(i);
			if(temp.burst_time > 0)
			{
				temp.quantum += 2;
				p.set(i, temp);
			}
		}
	}
	private static void quantum_double(ArrayList<AG> p)
	{
		int n = p.size();
		for(int i = 0; i < n; i++)
		{
			AG temp = p.get(i);
			if(temp.burst_time > 0)
			{
				temp.quantum *= 2;
				p.set(i, temp);
			}
		}
	}
	
	static void quantum_burst(ArrayList<AG> p)
	{
		quantum_increased(p);
		int n = p.size();
		for(int i = 0; i < n; i++)
		{
			AG temp = p.get(i);
			if(temp.burst_time > temp.quantum)
			{
				temp.burst_time -= temp.quantum;
				p.set(i, temp);
			}
			else
			{
				temp.burst_time -= temp.burst_time;
			}
		}
	}
	
	private static void calcwaittime(ArrayList<AG> p, int n, int wait[])
	{
		int[] rt = new int[n];
		for(int i = 0; i < n; i++)
		{
			rt[i] = p.get(i).burst_time;
			
		}
		int counter = 0, time = 0, minimum = Integer.MAX_VALUE;
		int shortest = 0, finishtime = 0, counting = 0;
		boolean check = false;
		while(counter != n)
		{
			for(int i = 0; i < n; i++)
			{
				if((p.get(i).arrival_time <= time) && (rt[i] < minimum) && (rt[i] > 0))
				{
					minimum = rt[i];
					shortest = i;
					check = true;
					counting++;
				}
			}
			if(check == false)
			{
				time++;
				continue;
			}
			rt[shortest]--;
			minimum = rt[shortest];
			if(minimum == 0)
			{
				minimum = Integer.MAX_VALUE;
			}
			if(rt[shortest] == 0)
			{
				counter++;
				check = false;
				finishtime = time + 1;
				
				wait[shortest] = finishtime - p.get(shortest).arrival_time;
				System.out.println("pname: "+p.get(shortest).name+"   finish:  "+finishtime+"  wait: "+wait[shortest]);
				if(wait[shortest] < 0)
				{
					wait[shortest] = 0;
				}
			}
			time++;
		}
	}
	
	public static void sjfmain(ArrayList<AG> p, int n)
	{
		int wait[] = new int[n];
		int turnaround[] = new int[n];
		quantum_double(p);
		calcturnaroundtime(p, n, wait, turnaround);
		//calcwaittime(p,n,wait);
		int avrgwait = 0;
		int avrgturn = 0;
		for(int i = 0; i < n; i++)
		{
			avrgwait += wait[i];
			avrgturn += turnaround[i];
		}
		clear_sjf( p,  n);
		System.out.println("in SJF");
		Print(p);
		avg+=(float)avrgwait/p.size();
		avg_TT+=(float)avrgturn/p.size();
	}
	
	public static void priorty_premativ(ArrayList<AG> p_queue)
	{
		Print(p_queue);
		int burst[] = new int[p_queue.size()];
		for(int i = 0; i < p_queue.size(); i++)
		{
			burst[i] = p_queue.get(i).burst_time;
		}
		Arrival_Sort(p_queue);
		LULU_Sort(p_queue);
		int n = p_queue.size();
		//int wait[] = new int[n];
		int wait[] = new int[n];
		int turnaround[] = new int[n];
		
		calcwaittime(p_queue,n,wait);
		int avrgwait = 0;
		int avrgturn = 0;
		for(int i = 0; i < n; i++)
		{
			avrgwait += wait[i];
		}
		
		Print(p_queue);
		avg+=(float)avrgwait/p_queue.size();
		avg_TT+=(float)avrgturn/p_queue.size();
		//**********************************************************
		quantum_burst(p_queue);
		
		for(int i = 0; i < n; i++)
		{
			avrgwait += wait[i];
		}
		AG proc[]=new AG [p_queue.size()];
		for (int i =0 ; i < p_queue.size();i++)
		{
			proc[i]= p_queue.get(i);
		}
		sjfmain (p_queue ,p_queue.size());
		System.out.println("The final avg = "+avg);
	}
	public static void clear_sjf(ArrayList<AG> p, int n)
	{
		
		for(int i = 0; i < n; i++)
		{
			if (p.get(i).burst_time>0)
			{
				P_name.add(p.get(i).name);
				p.get(i).burst_time=0;
			}
		}
	}
	public static void calcturnaroundtime(ArrayList<AG> p, int n, int wait[], int turnaround[])
	{
		for(int i = 0; i < n; i++)
		{
			turnaround[i] = p.get(i).burst_time + wait[i];
		}
	}
	
	public void AG_Main()
	{
		int num_of_processes = 0;
		Scanner input = new Scanner(System.in);
		ArrayList<AG> queue = new ArrayList<AG>();
		System.out.print("Please enter the number of process : ");
		num_of_processes = input.nextInt();
		Get_input(num_of_processes, queue);
		FCFS(queue);
		System.out.println(P_name);
		System.out.println(P_time);
	}
}