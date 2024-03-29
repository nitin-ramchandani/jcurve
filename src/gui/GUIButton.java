package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

import utils.ResourceManager;

/**
 * Ein Button, der eine gewisse Aktion ausf�hren kann. Der Button kann entweder
 * beschriftet sein, wie etwa ein Men�punkt, oder einfach eine mit Farbe
 * gef�llte Fl�che sein, zB. wenn der Spieler seine Farbe w�hlt.
 * 
 * @author Benjamin
 */
public class GUIButton extends BasicGUIElement {

	private String text = null;

	private Graphics texture = null;
	private GUIContext context = null;
	private MouseOverArea area = null;

	private Object value = null;

	private Color normalColor = Color.white;
	private Color mouseOverColor = Color.orange;
	private Color disabledColor = Color.gray;
	private Color fillColor;
	
	private boolean active = false;

	/**
	 * Erstellt einen neuen Button an der bestimmten Position.
	 * 
	 * @param text
	 *            - der Text des Button
	 * @param context
	 *            - der GUIContext (das GameContainer-Objekt der init, render
	 *            oder update-Methoden)
	 * @param x
	 *            - die xPos
	 * @param y
	 *            - die yPos
	 */
	public GUIButton(String text, GUIContext context, float x, float y) {
		this(text, context, x, y, "standard");
	}

	/**
	 * @see GUIButton#GUIButton(String, GUIContext, float, float)
	 */
	public GUIButton(String text, GUIContext context, float x, float y, String fontName) {
		super(x, y, ResourceManager.getFont(fontName).getWidth(text), ResourceManager.getFont(fontName).getHeight(text));

		this.text = text;
		this.context = context;

		Image button = null;
		try {
			button = new Image(getWidth(), getHeight());
			texture = button.getGraphics();
			texture.setFont(ResourceManager.getFont(fontName));
		} catch (SlickException e) {
			e.printStackTrace();
		}

		Rectangle shape = new Rectangle(x, y, getWidth(), getHeight());

		area = new MouseOverArea(context, button, shape);
		area.setNormalColor(normalColor);
		area.setMouseOverColor(mouseOverColor);
	}

	/**
	 * @see GUIButton#GUIButton(String, GUIContext, float, float)
	 */
	public GUIButton(GUIContext context, Color color, float x, float y, int width, int height) {
		super(x, y, width, height);

		this.text = "";
		this.context = context;
		this.fillColor = color;

		Image button = null;
		try {
			button = new Image(getWidth(), getHeight());
			texture = button.getGraphics();
			texture.setColor(fillColor);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		Rectangle shape = new Rectangle(x, y, getWidth(), getHeight());

		area = new MouseOverArea(context, button, shape);
		area.setNormalColor(normalColor);
		area.setMouseOverColor(mouseOverColor);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		if (text != "")
			texture.drawString(text, 0, 0);
		else {
			if(active){
				texture.setColor(Color.white);
				texture.fillRect(0, 0, getWidth(), getHeight());
				texture.setColor(getFillColor());
				texture.fillRect(3, 3, getWidth()-6, getHeight()-6);
			} else {
				texture.fillRect(0, 0, getWidth(), getHeight());
			}
		}

		texture.flush();
		area.render(context, g);

	}

	/**
	 * Legt die Textfarbe des Buttons fest.
	 * 
	 * @param c
	 *            - die Farbe
	 */
	public void setNormalColor(Color c) {
		normalColor = c;
		area.setNormalColor(normalColor);
	}

	/**
	 * Legt die MouseOver-Farbe des Buttons fest.
	 * 
	 * @param c
	 *            - die Farbe
	 */
	public void setMouseOverColor(Color c) {
		mouseOverColor = c;
		area.setMouseOverColor(mouseOverColor);
	}

	/**
	 * Legt die Farbe f�r den Button im deaktivierten Zustand fest.
	 * 
	 * @param c
	 *            - die Farbe
	 */
	public void setDisabledColor(Color c) {
		disabledColor = c;
	}

	/**
	 * Liefert die MouseOverArea dieses Button.
	 * 
	 * @return MouseOverArea
	 */
	public MouseOverArea getMouseOverArea() {
		return area;
	}

	/**
	 * Gibt an, ob dieser Button aktiviert ist, d.h. auf Eingaben reagiert.
	 * 
	 * @return boolean
	 */
	public boolean isEnabled() {
		return area.isAcceptingInput();
	}

	/**
	 * Setzt diesen Button aktiv, bzw. inaktiv.
	 * 
	 * @param enable
	 *            - true f�r aktiv
	 */
	public void setEnabled(boolean enable) {
		if (enable) {
			area.setNormalColor(normalColor);
			area.setMouseOverColor(mouseOverColor);
			area.setAcceptingInput(true);
		} else {
			area.setNormalColor(disabledColor);
			area.setMouseOverColor(disabledColor);
			area.setAcceptingInput(false);
		}
	}

	/**
	 * F�gt dem Button einen Listener hinzu, der auf Aktionen reagiert.
	 * 
	 * @param listener
	 *            - der ComponentListener
	 */
	public void addListener(ComponentListener listener) {
		area.addListener(listener);
	}

	/**
	 * Liefert die F�llfarbe des Buttons, falls vorhanden.
	 * 
	 * @return Color
	 */
	public Color getFillColor() {
		return fillColor;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
