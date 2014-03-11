#include<stdlib.h>
#include<cstdio>


int mySquare(int a){
	if (a<=0){
		return 0;
	}
	return a*a;
}

int squareCalculator(int a, int * arr){
    /*
    calculate square of postive integer numbers, return 0 if the number is smaller than 0
    */
	if (a == 1){
        return mySquare(arr[0]);
	}
    return mySquare(arr[a-1]) + squareCalculator(a-1, arr);
}

void readAnArray(int index, int *arr){
    /*
    read values for the array from keyboard
    */
    if (index==0)
        return;
    int x;
    scanf("%d", &x);
    arr[index-1] = x;
    readAnArray(index-1, arr);
}



void computerize(int x, int *rsArr){
    /*Calculate sum of square of positive integers and put its value to rsArr

    */
    if (x==0){
        return;
    }

     int m;
     scanf("%d", &m);
     int *tempArr ;
     tempArr = (int *) malloc(m*sizeof(int));
     readAnArray(m, tempArr);
     rsArr[x-1] = squareCalculator(m, tempArr);
     computerize(x-1, rsArr);
   
}
void printResult(int index, int *rs){
    if (index == 0)
    {
        return;
    }
    printf("%d\n", rs[index-1]);
    printResult(index-1, rs);
}
int main(){
	
       
    int n;
    int args;


    int *rsArr;
   

    
      if (( args = scanf("%d", &n)) == 0) {
          printf("Error: not an integer\n");
      } else {
          int *rsArray;
          rsArray = (int *) malloc(n*sizeof(int));
         
           computerize(n, rsArray);
           printResult(n, rsArray);

      }
	
 
	return 0;

}
