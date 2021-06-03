#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <sys/types.h>

#include "Queue.h"
#define DEBUG
#ifdef DEBUG
#define DEBUG_PRINT printf
#else
#define DEBUG_PRINT(...)
#endif

void * add_processing_queue (void * args);
void * process_nodes (void * args);
void * send_to_smemory (void * args);

pthread_mutex_t lock_input;     //  lock access to input variable
pthread_mutex_t lock_plist;     //  lock access to processing_queue
pthread_mutex_t lock_slist;     //  lock access to sending_queue

sem_t input_ready;      //  indicate main thread has a reading waiting
sem_t input_taken;      //  indicate when add_to_processing_queue has taken the reading from main thread
sem_t empty_pslots;     //  counting sem to indicate to the Add_To_Processing_Queue thread how many slots available and if it needs to wait to store into the Processing_Queue
sem_t filled_pslots;    //  counting sem to indicate when add_to_processing_queue thread has added an item to the processing_queue & used by process_nodes to know when an item has been added to the queue
sem_t empty_sslots;     //  counting sem to the process_node thread how many slots available and if it needs to wait for a slot to save to sending_queue 
sem_t filled_sslots;    //  counting sem to indicate when the process_nodes added an item to the sending_queue and used by send_to_smemory to wait till there is an item to send from queue

int input;
int end_time = 0;
Qnode * tmp;
Queue_Info PQ;
Queue_Info SQ;

int main() {
    int itr=1;
    void *ret;
    pthread_mutex_init (&lock_input, NULL);
    
    sem_init(&input_ready,0,0);
    sem_init(&input_taken,0,0);
    sem_init(&empty_pslots,0,5);
    sem_init(&filled_pslots,0,0);
    sem_init(&empty_sslots,0,5);
    sem_init(&filled_pslots,0,0);
    
    pthread_t add_processing_queue_thread;
    pthread_t process_nodes_thread;
    pthread_t send_to_smemory_thread;
    
    pthread_create(&add_processing_queue_thread,NULL,add_processing_queue,NULL);
    pthread_create(&process_nodes_thread,NULL,process_nodes,NULL);
    pthread_create(&send_to_smemory_thread,NULL,send_to_smemory,NULL);
    
    while(end_time != -1) {
        printf("Enter temp(C) reading %d: ", itr);
        itr++;
        pthread_mutex_lock(&lock_input);
        scanf("%d", &input);
        end_time = input;
        tmp = (Qnode *) malloc(sizeof(Qnode));
        tmp->data = input;
        

        
        pthread_mutex_unlock(&lock_input);
        sem_post(&input_ready);
        sem_wait(&input_taken); // wait for value to be ingested
    }

    pthread_join (add_processing_queue_thread, &ret);
    pthread_join (process_nodes_thread, &ret);
    pthread_join (send_to_smemory_thread, &ret);
    

    system("./RUnit");
    
    return 0;
}

void * add_processing_queue (void * args) {
    
    while(end_time != -1) {
        sem_wait(&input_ready);
        sem_wait(&empty_pslots);
        pthread_mutex_lock(&lock_plist);
        Enqueue(&PQ, tmp);        
        pthread_mutex_unlock(&lock_plist);
        sem_post(&input_taken);
        sem_post(&filled_pslots);
    }
}

void * process_nodes (void * args) {
    int valC = 0;
    int valF = 0;
    
    while(end_time != -1) {    
        // get input
        sem_wait(&filled_pslots);
        pthread_mutex_lock(&lock_plist);
        Dequeue(&PQ);
        pthread_mutex_unlock(&lock_plist);
        sem_post(&empty_pslots);
        
        // process input from queue
        valC= tmp->data;
        valF= ((valC*(9/5))+32);
        tmp->data = valF;
        
        // send processed input to next queue
        sem_wait(&empty_sslots);
        pthread_mutex_lock(&lock_slist);
        Enqueue(&SQ,tmp);
        pthread_mutex_unlock(&lock_slist);
        sem_post(&filled_sslots);
        
    }
}

void * send_to_smemory (void * args) {
    int valWrite = 0;
    const int SIZE = 4096;
	const int INPUTSIZE = 128;
	char input[INPUTSIZE];
	const char *name = "calculated_data";
    int shm_fd;
    void *ptr;
 	/* create the shared memory segment */
	shm_fd = shm_open(name, O_CREAT | O_RDWR, 0666);
 	/* configure the size of the shared memory segment */
	ftruncate(shm_fd,SIZE);
	/* now map the shared memory segment in the address space of the process */
	ptr = mmap(NULL,SIZE, PROT_READ | PROT_WRITE, MAP_SHARED, shm_fd, 0);
	
    while(end_time != -1) {
        sem_wait(&filled_sslots);
        pthread_mutex_lock(&lock_slist);
        Dequeue(&SQ);
        valWrite=tmp->data;

        sprintf(ptr,"%d",valWrite);
        ptr += sizeof(int);
        pthread_mutex_unlock(&lock_slist);
        sem_post(&empty_sslots);
        
      
    }
}


void Enqueue(Queue_Info *Q, Qnode * p){
     if(p == NULL){
         perror("No node to add ");
     }
     if(Q->first == NULL){
         Q->Queue = p;
        Q->first = p;
        Q->Last = p;
     }else{
     p->next = NULL;
     Q->Last->next = p; 
     Q->Last = p;
     }
     return;
 }
 
 Qnode * Dequeue(Queue_Info *Q){
     if(Q == NULL){
         perror("No Queue to search");
     }
     if(Q->first == NULL || Q->Last == NULL || Q->Queue == NULL){
         perror("Empty Queue, Nothing to remove");
         return NULL;
     }
     Qnode *p = Q->first;
     Q->first = Q->first->next;
     Q->Queue = Q->first;
     return p;
 }
 
  Qnode* Pop(Queue_Info *S){
     if(S== NULL){
         perror("No Stack to Pop");
     }
     if(S->first == NULL){
         perror("Empty Queue, Nothing to remove");
         return NULL;
     }
     return Dequeue(S);
 }