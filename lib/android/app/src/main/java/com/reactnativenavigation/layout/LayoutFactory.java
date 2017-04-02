package com.reactnativenavigation.layout;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.layout.bottomtabs.BottomTabs;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsLayout;
import com.reactnativenavigation.utils.CompatUtils;

import java.util.List;

public class LayoutFactory {

	private final Activity activity;
	private ReactNativeHost reactNativeHost;

	public LayoutFactory(Activity activity, ReactNativeHost reactNativeHost) {
		this.activity = activity;
		this.reactNativeHost = reactNativeHost;
	}

	public View create(LayoutNode node) {
		switch (node.type) {
			case Container:
				return createContainerView(node);
			case ContainerStack:
				return createContainerStackView(node);
			case BottomTabs:
				return createBottomTabs(node);
			case SideMenuRoot:
				return createSideMenuRoot(node);
			case SideMenuCenter:
				return createSideMenuContent(node);
			case SideMenuLeft:
				return createSideMenuLeft(node);
			case SideMenuRight:
				return createSideMenuRight(node);
			default:
				throw new IllegalArgumentException("Invalid node type: " + node.type);
		}
	}

	private View createSideMenuRoot(LayoutNode node) {
		SideMenuLayout sideMenuLayout = new SideMenuLayout(activity);
		for (LayoutNode child : node.children) {
			sideMenuLayout.addView(create(child));
		}
		return sideMenuLayout;
	}

	private View createSideMenuContent(LayoutNode node) {
		return create(node.children.get(0));
	}

	private View createSideMenuLeft(LayoutNode node) {
		View view = create(node.children.get(0));
		view.setId(CompatUtils.generateViewId());
		DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.LEFT;
		view.setLayoutParams(lp);
		return view;
	}

	private View createSideMenuRight(LayoutNode node) {
		View view = create(node.children.get(0));
		view.setId(CompatUtils.generateViewId());
		DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.RIGHT;
		view.setLayoutParams(lp);
		return view;
	}

	private View createContainerView(LayoutNode node) {
		final String name = node.data.optString("name");
		Container container = new Container(activity, reactNativeHost, node.id, name);
		container.setId(CompatUtils.generateViewId());
		return container;

	}

	private View createContainerStackView(LayoutNode node) {
		final ContainerStackLayout containerStack = new ContainerStackLayout(activity);
		containerStack.setId(CompatUtils.generateViewId());
		addChildrenNodes(containerStack, node.children);
		return containerStack;
	}

	private View createBottomTabs(LayoutNode node) {
		final BottomTabsLayout tabsContainer = new BottomTabsLayout(activity, new BottomTabs());
		for (int i = 0; i < node.children.size(); i++) {
			final View tabContent = create(node.children.get(i));
			tabsContainer.addTabContent("#" + i, tabContent);
		}
		return tabsContainer;
	}

	private void addChildrenNodes(ContainerStackLayout containerStack, List<LayoutNode> children) {
		for (LayoutNode child : children) {
			containerStack.addView(create(child));
		}
	}
}