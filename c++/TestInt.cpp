#include <vector>

using namespace std;
vector<int> myVec;

extern "C" void pushItem(int i) {
    myVec.push_back(i+1);
}

extern "C" void getResult (int* buf, size_t len) {
    copy(myVec.begin(), myVec.end(), buf);
}