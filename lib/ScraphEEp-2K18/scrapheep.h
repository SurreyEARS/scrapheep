// ensure this library description is only included once
#ifndef scrapheep_h
#define scrapheep_h

// include types & constants of Wiring core API
#include "WConstants.h"

// library interface description
class Test
{
  // user-accessible "public" interface
  public:
    Test(int);
    void doSomething(void);

  // library-accessible "private" interface
  private:
    int value;
    void doSomethingSecret(void);
};

#endif
