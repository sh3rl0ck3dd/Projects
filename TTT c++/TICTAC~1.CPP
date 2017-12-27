#include <iostream.h>
#include <conio.h>
#include <string.h>
#include <stdio.h>
#include<fstream.h>

char player1[30],player2[30],currplayer[30];
char checkPosition[10] = {'o','1','2','3','4','5','6','7','8','9'};
int checkResult();
void board();

int main()
{
	int player = 1	,i,choice;
	char mark;
	fstream file;
	clrscr();

	cout<<"enter first player's name ->  ";
	scanf("%[^\n]%*c",player1);
	cout<<"enter second player's name -> ";
	scanf("%[^\n]%*c",player2);

	do
	{
		board();
		player=(player%2)?1:2;
		if(player==1){
		strcpy(currplayer,player1);
		   }
		   else{
		   strcpy(currplayer,player2);
		   }
		cout << "Player " <<currplayer << ", enter a number:  ";
		cin >> choice;
		mark=(player == 1) ? 'X' : 'O';
		if (choice == 1 && checkPosition[1] == '1')
			checkPosition[1] = mark;
		else if (choice == 2 && checkPosition[2] == '2')
			checkPosition[2] = mark;
		else if (choice == 3 && checkPosition[3] == '3')
			checkPosition[3] = mark;
		else if (choice == 4 && checkPosition[4] == '4')
			checkPosition[4] = mark;
		else if (choice == 5 && checkPosition[5] == '5')
			checkPosition[5] = mark;
		else if (choice == 6 && checkPosition[6] == '6')
			checkPosition[6] = mark;
		else if (choice == 7 && checkPosition[7] == '7')
			checkPosition[7] = mark;
		else if (choice == 8 && checkPosition[8] == '8')
			checkPosition[8] = mark;
		else if (choice == 9 && checkPosition[9] == '9')
			checkPosition[9] = mark;
			else
		{
			cout<<"Invalid move ";
			player--;
			getch();
		}
		i=checkResult();
		player++;
	}while(i==-1);
	board();
	if(i==1){

		cout<<"==>\aPlayer "<<currplayer<<" win ";
		file.open("TTTdb.txt", ios :: out | ios :: app);
		if (file.fail()) {
		cout << "Error Opening file ... " << endl;
				}
		  else {
		  file <<"platform c++"<<endl<<"players->"<<player1<<" and "<<player2<<endl<<"winner->"<< currplayer <<endl<<endl<< endl;
		  cout <<endl<< "data in the TTTdb stored" << endl;
   }

}
	else   {
		cout<<"==>\aGame draw";
		file.open("TTTdb.txt", ios :: out | ios :: app);
		if (file.fail()) {
		cout << "Error Opening file ... " << endl;
				}
		  else {
		  file <<"platform c++"<<endl<<"players->"<<player1<<" and "<<player2<<endl<<"DRAW" <<endl<<endl<< endl;
		  cout <<endl<< "data in the TTTdb stored" << endl;
   }
		}
	getch();
	return 0;
}


int checkResult()
{
	if ( checkPosition[1] == checkPosition[2] && checkPosition[2] == checkPosition[3] ||
	     checkPosition[4] == checkPosition[5] && checkPosition[5] == checkPosition[6] ||
	     checkPosition[1] == checkPosition[4] && checkPosition[4] == checkPosition[7] ||
	     checkPosition[2] == checkPosition[5] && checkPosition[5] == checkPosition[8] ||
	     checkPosition[3] == checkPosition[6] && checkPosition[6] == checkPosition[9] ||
	     checkPosition[1] == checkPosition[5] && checkPosition[5] == checkPosition[9] ||
	     checkPosition[3] == checkPosition[5] && checkPosition[5] == checkPosition[7])
	     {
		return 1;
	     }
	else if (checkPosition[1] != '1' && checkPosition[2] != '2' && checkPosition[3] != '3' &&
		 checkPosition[4] != '4' && checkPosition[5] != '5' && checkPosition[6] != '6' &&
		 checkPosition[7] != '7' && checkPosition[8] != '8' && checkPosition[9] != '9')
	       {
		return 0;
		}
	else   {
		return -1;
		}
}



void board()
{
	clrscr();
	cout << "\n\n\tTic Tac Toe\n\n";
	cout << player1<< "(X)  -"<<player2<<" (O)" << endl << endl;
	cout << endl;
	cout << "     |     |     " << endl;
	cout << "  " << checkPosition[1] << "  |  " << checkPosition[2] << "  |  " << checkPosition[3] << endl;
	cout << "_____|_____|_____" << endl;
	cout << "     |     |     " << endl;
	cout << "  " << checkPosition[4] << "  |  " << checkPosition[5] << "  |  " << checkPosition[6] << endl;
	cout << "_____|_____|_____" << endl;
	cout << "     |     |     " << endl;
	cout << "  " << checkPosition[7] << "  |  " << checkPosition[8] << "  |  " << checkPosition[9] << endl;
	cout << "     |     |     " << endl << endl;
}
