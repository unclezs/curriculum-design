package com.unclezs.flight.ui;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/*
 *@author unclezs.com
 *@date 2019.06.05 22:16
 */
class GlobalMenu extends ContextMenu
{
    /**
     * 单例
     */
    private static GlobalMenu INSTANCE = null;

    /**
     * 私有构造函数
     */
    private GlobalMenu()
    {
        MenuItem buy = new MenuItem("购买");
        MenuItem delete = new MenuItem("删除");
        MenuItem cancel = new MenuItem("取消");


        getItems().add(buy);
        getItems().add(delete);
        getItems().add(cancel);
    }

    /**
     * 获取实例
     * @return GlobalMenu
     */
    public static GlobalMenu getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new GlobalMenu();
        }

        return INSTANCE;
    }
}