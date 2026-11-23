package util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * Palet warna dan komponen UI bertema Burgundy untuk SIKURSUS Desktop (Java 8 / Swing).
 */
public final class ThemeBurgundy {

    // --- Palet Warna Utama (Burgundy) ---
    public static final Color BURGUNDY_PRIMARY   = new Color(123, 30, 58);   // #7B1E3A
    public static final Color BURGUNDY_DARK      = new Color(88, 17, 39);    // #581127
    public static final Color BURGUNDY_HOVER     = new Color(105, 22, 48);   // #691630
    public static final Color BURGUNDY_ACCENT    = new Color(158, 42, 77);   // #9E2A4D
    public static final Color BURGUNDY_SOFT_BG   = new Color(248, 232, 237); // #F8E8ED
    public static final Color BURGUNDY_SOFT_TEXT = new Color(123, 30, 58);   // #7B1E3A

    // --- Warna Netral & Permukaan ---
    public static final Color BG_MAIN            = new Color(247, 245, 246); // #F7F5F6
    public static final Color BG_WHITE           = Color.WHITE;
    public static final Color BG_DISABLED        = new Color(240, 236, 238); // #F0ECEE
    public static final Color BG_SECONDARY_BTN   = new Color(241, 241, 243); // #F1F1F3
    public static final Color BG_SECONDARY_HOVER = new Color(228, 228, 232);

    public static final Color BORDER_LIGHT       = new Color(226, 220, 223); // #E2DCDF
    public static final Color BORDER_INPUT       = new Color(208, 200, 204);

    public static final Color TEXT_PRIMARY       = new Color(33, 24, 28);    // #21181C
    public static final Color TEXT_SECONDARY     = new Color(108, 95, 100);  // #6C5F64
    public static final Color TEXT_MUTED         = new Color(145, 134, 139);

    public static final Color LOCKED_BG          = new Color(252, 232, 236); // #FCE8EC
    public static final Color LOCKED_TEXT        = new Color(180, 35, 62);   // #B4233E

    // --- Font Standar ---
    public static final Font FONT_TITLE_LG   = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_TITLE_MD   = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SECTION    = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY       = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD  = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SMALL      = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SMALL_ITALIC = new Font("Segoe UI", Font.ITALIC, 12);
    public static final Font FONT_BADGE      = new Font("Segoe UI", Font.BOLD, 10);

    private ThemeBurgundy() {
    }

    /**
     * Panel dengan gradasi Burgundy dan dekorasi lingkaran transparan seperti desain referensi.
     */
    public static class BurgundyBannerPanel extends JPanel {
        private final int arc;

        public BurgundyBannerPanel(int arc) {
            this.arc = arc;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, BURGUNDY_PRIMARY, w, h, BURGUNDY_DARK);
            g2.setPaint(gp);

            if (arc > 0) {
                RoundRectangle2D clip = new RoundRectangle2D.Float(0, 0, w, h, arc, arc);
                g2.fill(clip);
                g2.setClip(clip);
            } else {
                g2.fillRect(0, 0, w, h);
            }

            // Dekorasi lingkaran transparan
            g2.setColor(new Color(255, 255, 255, 22));
            g2.fillOval((int) (w * 0.65), -h / 3, (int) (h * 1.4), (int) (h * 1.4));
            g2.setColor(new Color(255, 255, 255, 14));
            g2.fillOval(-h / 4, h / 3, (int) (h * 1.1), (int) (h * 1.1));
            g2.fillOval((int) (w * 0.25), -h / 2, h, h);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Panel kartu dengan sudut membulat dan warna border yang dapat diubah saat hover.
     */
    public static class RoundedCardPanel extends JPanel {
        private Color bgColor;
        private Color borderColor;
        private int borderThickness;
        private final int arc;

        public RoundedCardPanel(Color bgColor, Color borderColor, int arc) {
            this.bgColor = bgColor;
            this.borderColor = borderColor;
            this.borderThickness = 1;
            this.arc = arc;
            setOpaque(false);
        }

        public void setCardStyle(Color bgColor, Color borderColor, int borderThickness) {
            this.bgColor = bgColor;
            this.borderColor = borderColor;
            this.borderThickness = borderThickness;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, w - 1, h - 1, arc, arc);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));
            int offset = borderThickness / 2;
            g2.drawRoundRect(offset, offset, w - 1 - borderThickness, h - 1 - borderThickness, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Membuat tombol bergaya modern rounded.
     */
    public static JButton createRoundedButton(String text, final Color bg, final Color hoverBg,
            final Color fg, int arc) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(hoverBg.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverBg);
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(fg);
        btn.setFont(FONT_BODY_BOLD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Border input dengan warna khusus dan padding dalam.
     */
    public static Border createInputBorder(final Color borderColor, final int thickness) {
        return BorderFactory.createCompoundBorder(
                new AbstractBorder() {
                    @Override
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(borderColor);
                        g2.setStroke(new BasicStroke(thickness));
                        g2.drawRoundRect(x, y, width - 1, height - 1, 8, 8);
                        g2.dispose();
                    }

                    @Override
                    public Insets getBorderInsets(Component c) {
                        return new Insets(6, 10, 6, 10);
                    }
                },
                BorderFactory.createEmptyBorder(2, 4, 2, 4)
        );
    }

    /**
     * Ikon kotak huruf berwarna untuk item menu (P, I, K, dll).
     */
    public static Icon createLetterIcon(final String letter, final Color bgColor) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(x, y, getIconWidth(), getIconHeight(), 5, 5);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                int strW = g2.getFontMetrics().stringWidth(letter);
                int strH = g2.getFontMetrics().getAscent();
                g2.drawString(letter, x + (getIconWidth() - strW) / 2, y + (getIconHeight() + strH) / 2 - 2);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 18;
            }

            @Override
            public int getIconHeight() {
                return 18;
            }
        };
    }
}
