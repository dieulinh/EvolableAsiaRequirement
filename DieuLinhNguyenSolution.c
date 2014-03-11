#include<stdlib.h>
#include<cstdio>


int mySquare(int a){
	if (a<=0){
		return 0;
	}
	return a*a;
}

int squareCalculator(int a, int * arr){
	if (a == 1){
        return mySquare(arr[0]);
	}
    return mySquare(arr[a-1]) + squareCalculator(a-1, arr);
}

int main(){
	
       
    int n;
    int args;


    int *rsArr;
   

    
      if (( args = scanf("%d", &n)) == 0) {
          printf("Error: not an integer\n");
      } else {
         
           rsArr = (int *) malloc(n*sizeof(int));
          for (int i=0; i<n; i++){
              int m;
              scanf("%d", &m);
              
              int *tempArr ;
              tempArr = (int *) malloc(m*sizeof(int));
              for (int j = 0; j < m; j++){
                  scanf("%d", &tempArr[j]);
                  rsArr[i] = squareCalculator(m, tempArr);
                  
              }
              
              
          }
          for(int i=0;i<n;i++){
              printf("%d\n", rsArr[i]);
          }
      }
	
    getchar();
	return 0;

}
