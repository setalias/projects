#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <string.h>

int main()
{
	const char *name = "calculated_data";
	const int SIZE = 4096;
	int itr = 1;
	int shm_fd;
	
	void *ptr;
	
	int i;

	/* open the shared memory segment */
	shm_fd = shm_open(name, O_RDONLY, 0666);
	if (shm_fd == -1) {
		printf("shared memory failed\n");
		exit(-1);
	}

	/* now map the shared memory segment in the address space of the process */
	ptr = mmap(NULL,SIZE, PROT_READ, MAP_SHARED, shm_fd, 0);
	if (ptr == MAP_FAILED) {
		printf("Map failed\n");
		exit(-1);
	}

	/* now read from the shared memory region */
	
	printf("\n>>>>>>>>>> Reading from shared memory <<<<<<<<<<\n");
	
    char end[3] = "31";	
	printf("\n");
    while((strcmp((char *)ptr,end)) != 0) {
        
        printf("Reading %d (F): ", itr);
	    itr++;
	    printf("%s\n",(int *)ptr);
	    
	    
        
        
        ptr += sizeof(int);
        
        
	}
	
	/* remove the shared memory segment */
	if (shm_unlink(name) == -1) {
		printf("Error removing %s\n",name);
		exit(-1);
	}
	return 0;
}