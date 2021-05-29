/*
Written by: Sandy Lee
Purpose: 
  Inputs a txt document with a desired number and organization of train coaches
  Track A is the input track, trains always come in ascending numerical order
  Track B is the output track, which should hold the desired output order
  Station emulates a stack with LIFO. Once a train comes in from track A it can not go back onto track A at any time
  Determines whether or not the desired organization is possible and outputs to a file. 
*/

#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string> 
#include <stack> 
#include <vector>

using namespace std;

int main() {
    //file read and write    
    FILE* file;
    freopen_s(&file,"lab1in.txt", "r", stdin);
    freopen_s(&file,"lab1out_mine.txt", "w", stdout);

    // initialize vars
    stack<int> station;
    vector<int> trackB;
    vector<int> trackBrequest;
    int x = 0;
    int size = 0;
    int i = 0;
    int trackA = 0;
    int j = 0;
    int Bcounter = 0;
    bool repeat = true;
    bool first = true;
    bool consecutive = false;
    
    // algorithm logic
    while (true) {
        cin >> x;       //read size in
        size = x;          
        
        while (i < size) {                                      // repeat while < size
            if (first == true) {                                // if first loop per read block
                while (i < size) {                              // fill train sort request vector
                    cin >> x;
                    trackBrequest.push_back(x);
                    i++;
                }
                i = 0;
                trackA++;                                       // iterate to first input location from incoming train
                station.push(trackA);                        
                first = false;                               
            }
            if (Bcounter != size) {                             
                x = trackBrequest.at(Bcounter);              
            }

            if (Bcounter != size) {x = trackBrequest.at(Bcounter);} // ensure Bcounter hasn't escaped bounds of vector
            if (x == 0) {return 0;}                             // end program when 0 encountered in input file

            while (repeat) {                                    
                if (consecutive == false) {i++;}                // if haven't added to stack don't iterate i
                if (!station.empty()) {                         // ensure stack isn't empty
                                      
                    if (x == station.top()) {                   // if desired output on top of stack
                        trackB.push_back(station.top());        
                        station.pop();                          
                        repeat = false;
                        Bcounter++;
                        consecutive = true;
                    }
                    else {                                      // else iterate input onto stack    
                        trackA++;
                        station.push(trackA);
                        consecutive = false;
                    }
                    if (i == size) {break;}                    
                }
                else {
                    trackA++;
                    station.push(trackA);
                    consecutive = false;
                }
            }

            // if done then check if successful 
            if (i == size) {
                if (trackB.size() == size) {                    // does track B have all the cars on it
                    cout << "Yes\n";
                }
                else {
                    cout << "No\n";
                }
                // clear to prepare for next input
                trackB.clear();
                while (!station.empty()) {
                    station.pop();
                }
                trackA = 0;
                i = 0;
                first = true;
                trackBrequest.clear();
                Bcounter = 0;
            }
            repeat = true;
        }
    }
}
