#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <React/RCTConvert.h>
#import "RCCStatusBarOverlay.h"

@implementation RCCStatusBarOverlay

static UIWindow *_window = nil;
static UILabel *_label = nil;

+(void)showWithParams:(NSDictionary *)params
{
    dispatch_async(dispatch_get_main_queue(), ^{
        if (!_window) {
            CGFloat safeArea = 0;
            CGFloat normalHeight = 22;
            CGFloat minHeight = 17;
            if (@available(iOS 11.0, *)) {
                 safeArea = [[UIApplication sharedApplication] keyWindow].safeAreaInsets.top;
            }

            CGFloat screenWidth = [UIScreen mainScreen].bounds.size.width;
            _window = [[UIWindow alloc] initWithFrame: safeArea > normalHeight
                                                        ? CGRectMake(0, 0, screenWidth, safeArea + 2)
                                                        : CGRectMake(0, 0, screenWidth, normalHeight)];

            _window.windowLevel = UIWindowLevelStatusBar + 1;

            // set a root VC so rotation is supported
            _window.rootViewController = [UIViewController new];

            _label = [[UILabel alloc] initWithFrame: safeArea > normalHeight
                                                        ? CGRectMake(0, safeArea + 2 - minHeight, screenWidth, minHeight)
                                                        : CGRectMake(0, 0, screenWidth, normalHeight)];
            _label.font = [UIFont systemFontOfSize:14.0];
            _label.textAlignment = NSTextAlignmentCenter;

            [_window addSubview:_label];
        }

        _label.text = params[@"text"];
        _label.textColor = [RCTConvert UIColor:params[@"textColor"]];
        _window.backgroundColor = [RCTConvert UIColor:params[@"backgroundColor"]];
        _window.hidden = NO;
    });
}

+(void)dismissWithParams:(NSDictionary *)params
{
    dispatch_async(dispatch_get_main_queue(), ^{
        CGRect windowFrame = _window.frame;
        [UIView animateWithDuration:0.25
                              delay:0
                            options:0
                         animations:^{
                             _window.frame = CGRectOffset(windowFrame, 0, -windowFrame.size.height);
                         } completion:^(__unused BOOL finished) {
                             _window.frame = windowFrame;
                             _window.hidden = YES;
                             _window = nil;
                             _label = nil;
                         }];
    });
}

@end
