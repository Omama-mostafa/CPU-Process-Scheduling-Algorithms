package Scheduling;

import java.util.ArrayList;
import java.util.Scanner;

public class Round_Robin
{
	
	private String P;
	private int burst_Time = 0;
	private int arrival_Time = 0;
	private int space;
	private static ArrayList<Round_Robin> process = new ArrayList<>();
	private static ArrayList<Round_Robin> pprocess = new ArrayList<>();
	private static ArrayList<Round_Robin> time = new ArrayList<>();
	private static ArrayList<String> p = new ArrayList<>();
	private static ArrayList<String> pp = new ArrayList<>();
	private static ArrayList<Integer> brust = new ArrayList<>();
	private static ArrayList<Integer> arrival = new ArrayList<>();
	private static ArrayList<Integer> Space = new ArrayList<>();
	private static int quantum = 0;
	private static int context_switching = 0;
	
	private static Round_Robin get_Max(ArrayList<Round_Robin> Array_Round)
	{
		Round_Robin Temp = new Round_Robin();
		
		for(int i = 0; i < Array_Round.size(); i++)
		{
			for(int j = 0; j < Array_Round.size(); j++)
			{
				if(Array_Round.get(j).burst_Time < Array_Round.get(i).burst_Time)
				{
					Temp = Array_Round.get(i);
				}
			}
			
		}
		
		return Temp;
	}
	
	private static void Round_Robin()
	{
		int used_time = 0;
		System.out.println("-------------------------------------------------");
		System.out.println("process " + "    " + "Burst Time " + "     " + "arrivalTime");
		for(int i = 0; i < process.size(); i++)
		{
			System.out.println(process.get(i).P + "              " + process.get(i).burst_Time + "              "+ process.get(i).arrival_Time);
		}
		boolean flag = false;
		System.out.println("-------------------------------------------------");
		System.out.println("process " + "   " + "Time in process ");
		for(int j = 0; j < p.size(); j++)
		{
			
			if(process.get(j).arrival_Time <= used_time)
			{
				if(process.get(j).burst_Time == 0)
				{
					j++;
				}
				else
				{
					if(j == 0)
					{
						if(process.get(j).burst_Time >= quantum)
						{
							used_time += quantum;
							Space.add(used_time);
							pp.add(process.get(j).P);
							process.get(j).burst_Time = process.get(j).burst_Time - quantum;
							System.out.println(process.get(j).P + "              " + process.get(j).burst_Time);
							
						}
						else
						{
							used_time += process.get(j).burst_Time;
							Space.add(used_time);
							pp.add(process.get(j).P);
							process.get(j).burst_Time = 0;
							System.out.println(process.get(j).P + "              " + process.get(j).burst_Time);
							
						}
					}
					else
					{
						if(process.get(j).burst_Time >= quantum)
						{
							used_time += quantum + context_switching;
							Space.add(used_time);
							pp.add(process.get(j).P);
							process.get(j).burst_Time = process.get(j).burst_Time - quantum;
							System.out.println(process.get(j).P +  "              " + process.get(j).burst_Time);
							
						}
						else
						{
							used_time += process.get(j).burst_Time + context_switching;
							Space.add(used_time);
							pp.add(process.get(j).P);
							process.get(j).burst_Time = 0;
							System.out.println(process.get(j).P +  "              " + process.get(j).burst_Time);
							
						}
						
					}
				}
				
			}
			else
			{
				if(j != 0)
				{
					if(process.get(j - 1).burst_Time != 0)
					{
						used_time += quantum + context_switching;
						process.get(j - 1).burst_Time = process.get(j - 1).burst_Time - quantum;
						Space.add(used_time);
						pp.add(process.get(j - 1).P);
						System.out.println(process.get(j - 1).P +  "              " + process.get(j - 1).burst_Time);
						
					}
					else
					{
						used_time += 1;
						j--;
					}
				}
				else
				{
					used_time += process.get(j).arrival_Time;
					if(process.get(j).burst_Time >= quantum)
					{
						used_time += quantum;
						Space.add(used_time);
						pp.add(process.get(j).P);
						process.get(j).burst_Time = process.get(j).burst_Time - quantum;
						System.out.println(process.get(j).P + "              " + process.get(j).burst_Time);
						
					}
					else
					{
						used_time += process.get(j).burst_Time;
						Space.add(used_time);
						pp.add(process.get(j).P);
						process.get(j).burst_Time = 0;
						System.out.println(process.get(j).P +  "              " + process.get(j).burst_Time);
						
					}
				}
			}
			if(j == p.size() - 1)
			{
				flag = true;
			}
			
		}
		int i = 0;
		Round_Robin maxx = new Round_Robin();
		
		maxx = get_Max(process);
		if(flag == true)
		{
			while(maxx.burst_Time != 0)
			{
				if(process.get(i).burst_Time == 0)
				{
					i++;
				}
				else
				{
					if(process.get(i).burst_Time >= quantum)
					{
						used_time += quantum + context_switching;
						Space.add(used_time);
						pp.add(process.get(i).P);
						process.get(i).burst_Time = process.get(i).burst_Time - quantum;
						System.out.println(process.get(i).P +  "              " + process.get(i).burst_Time);
						i++;
					}
					else
					{
						used_time += process.get(i).burst_Time + context_switching;
						Space.add(used_time);
						pp.add(process.get(i).P);
						process.get(i).burst_Time = 0;
						System.out.println(process.get(i).P +  "              " + process.get(i).burst_Time);
						i++;
					}
					
				}
				
				if(i == p.size())
				{
					i = 0;
				}
				
				maxx = get_Max(process);
				if(maxx.burst_Time == 0)
				{
					break;
				}
			}
			
		}
		for(int j = 0; j < pp.size(); j++)
		{
			Round_Robin R = new Round_Robin();
			R.space = Space.get(j);
			
			R.P = pp.get(j);
			time.add(R);
		}
		
		int wating = 0;
		int maxy = 0;
		int mmax = 0;
		int bbrust = 0;
		System.out.println("-------------------------------------------------");
		System.out.println("process " + "     " + "burst_Time" + "       " + " waiting time " + "     "+"turn around time");
		for(int j = 0; j < pprocess.size(); j++)
		{
			for(int z = 0; z < pp.size(); z++)
			{
				
				if(pprocess.get(j).P == time.get(z).P)
				{
					if(time.get(j).space <= time.get(z).space)
					{
						maxy = time.get(z).space;
						bbrust = pprocess.get(j).burst_Time;
					}
				}
				
			}
			mmax += maxy;
			
			wating += maxy - bbrust;
			//  System.out.println("maxy     " + maxy);
			System.out.println(pprocess.get(j).P +  "              " + bbrust + "                   " + (maxy - bbrust) + "                " +(wating+bbrust));
		}
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("The Avarage Wating time is " + (float) wating / pprocess.size());
		System.out.println("Average complitiontime time is  " + (float) mmax / pprocess.size());
	}
	
	public static void read_user()
	{
		int number_process = 0;
		String process_name = "";
		int burst_Tim = 0;
		int arrival_Time = 0;
		Scanner read = new Scanner(System.in);
		
		System.out.print("Enter the number of process : ");
		number_process = read.nextInt();
		System.out.print("Enter the context Switching : ");
		context_switching = read.nextInt();
		
		System.out.print("Enter the quantum : ");
		quantum = read.nextInt();
		
		System.out.println("Enter the name of process ");
		for(int i = 0; i < number_process; i++)
		{
			process_name = read.next();
			p.add(process_name);
		}
		
		System.out.println("Enter the Burst_Time");
		for(int i = 0; i < number_process; i++)
		{
			burst_Tim = read.nextInt();
			brust.add(burst_Tim);
		}
		System.out.println("Enter the arrival_Time");
		for(int i = 0; i < number_process; i++)
		{
			arrival_Time = read.nextInt();
			arrival.add(arrival_Time);
		}
		for(int i = 0; i < p.size(); i++)
		{
			Round_Robin R = new Round_Robin();
			R.P = p.get(i);
			R.burst_Time = brust.get(i);
			R.arrival_Time = arrival.get(i);
			process.add(R);
		}
		for(int i = 0; i < p.size(); i++)
		{
			Round_Robin R = new Round_Robin();
			R.P = p.get(i);
			R.burst_Time = brust.get(i);
			R.arrival_Time = arrival.get(i);
			pprocess.add(R);
			
		}
		Round_Robin();
	}
}
