package bonbombe.views.utils;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.color.*;
import java.awt.Graphics;
import java.util.*;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.util.Random;
import java.io.*;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Carre extends JButton {
	private int row, col;
	private String name;
	
	public Carre(int row, int col) {

		super();
		setColour(Color.gray);
		this.row = row;
		this.col = col;
		this.name = row + "," + col;
	}
	
	public void setColour(Color c) {
		
		this.setBackground(c);
	}
	
	public int getCol() {
		
		return col;
	}
	
	public int getRow() {

		return row;
	}
	
	public String getName() {
		
		return name;
	}
}