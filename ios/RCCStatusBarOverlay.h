
#import <Foundation/Foundation.h>
#import "RCCManager.h"

@interface RCCStatusBarOverlay : NSObject
+(void)showWithParams:(NSDictionary*)params;
+(void)dismissWithParams:(NSDictionary*)params;
@end

