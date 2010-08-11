package org.chartsy.main;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.chartsy.main.features.FeaturesPanel;
import org.chartsy.main.managers.ProxyManager;
import org.chartsy.main.utils.DesktopUtil;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.windows.WindowManager;

/**
 *
 * @author viorel.gheba
 */
public class RegisterForm extends javax.swing.JDialog {

	private static final Logger LOG
		= Logger.getLogger(RegisterForm.class.getPackage().getName());

    public RegisterForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

		usedCookies.clear();
        usedCookies.add("PHPSESSID");
        usedCookies.add("amember_nr");

        initComponents();
        setTitle("Register");
        parent.setIconImage(WindowManager.getDefault().getMainWindow().getIconImage());
        getRootPane().setDefaultButton(btnRegister);
        initForm();
    }

    private void initForm()
	{
        lblTop.addMouseListener(new MouseListener()
		{
            public void mouseClicked(MouseEvent e)
			{
                try
				{
					DesktopUtil.browse(NbBundle.getMessage(RegisterForm.class, "MrSwing_URL"));
				}
                catch (Exception ex)
				{
					LOG.log(Level.SEVERE, null, ex);
				}
            }
            public void mouseEntered(MouseEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
            public void mouseExited(MouseEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnRemind = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTop.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.lblTop.text")); // NOI18N

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUsername.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.lblUsername.text")); // NOI18N

        txtUsername.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.txtUsername.text")); // NOI18N

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPassword.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.lblPassword.text")); // NOI18N

        lblMessage.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.lblMessage.text")); // NOI18N

        btnRegister.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.btnRegister.text")); // NOI18N
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnRemind.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.btnRemind.text")); // NOI18N
        btnRemind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemindActionPerformed(evt);
            }
        });

        txtPassword.setText(org.openide.util.NbBundle.getMessage(RegisterForm.class, "RegisterForm.txtPassword.text")); // NOI18N

        org.jdesktop.layout.GroupLayout pnlMainLayout = new org.jdesktop.layout.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .add(lblTop, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .add(pnlMainLayout.createSequentialGroup()
                        .add(btnRegister)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(btnRemind))
                    .add(pnlMainLayout.createSequentialGroup()
                        .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblUsername)
                            .add(lblPassword))
                        .add(28, 28, 28)
                        .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(txtUsername, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtPassword, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblTop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblUsername)
                    .add(txtUsername, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblPassword)
                    .add(txtPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(lblMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnRegister)
                    .add(btnRemind))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemindActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btnRemindActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        String username = txtUsername.getText();
        String pass = new String(txtPassword.getPassword());
        String password = null;
		Preferences p = NbPreferences.root().node("/org/chartsy/register");

        try
		{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(new String(txtPassword.getPassword()).getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            password = hash.toString(16);
        } 
		catch (NoSuchAlgorithmException ex)
		{
            LOG.log(Level.SEVERE, null, ex);
        }

        try 
		{
			lblMessage.setText("Checking registration ...");
			btnRegister.setEnabled(false);
			btnRemind.setEnabled(false);
			
			HttpClient client = ProxyManager.getDefault().getHttpClient();
			GetMethod method = new GetMethod(NbBundle.getMessage(RegisterForm.class, "CheckReg_URL"));

			method.setQueryString(new NameValuePair[]
			{
				new NameValuePair("username", username),
				new NameValuePair("password", password)
			});

			client.executeMethod(method);
			BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));

			boolean chartsyRegistred = false;
			String name = null;
            if (br != null)
			{
                String firstLine = br.readLine();
                if (firstLine.equals("OK"))
				{
					chartsyRegistred = true;
                    name = br.readLine();
                    p.putBoolean("registred", true);
                    p.put("name", name);
                    p.put("date", String.valueOf(new Date().getTime()));
                    p.put("username", username);
                    p.put("password", pass);
                }
            }

			boolean mrSwingRegistred = checkMrSwingRegistration();
			p.putBoolean("mrswingregistred", mrSwingRegistred);

			int userId = checkStockScanPRORegistration();
			boolean stockScanPRORegistred = userId != 0;

			Preferences prefs = NbPreferences.root().node("/org/chartsy/stockscanpro");
			prefs.putBoolean("stockscanproregistred", stockScanPRORegistred);
			prefs.putInt("stockscanprouser", userId);

			FeaturesPanel.getDefault().hideBanners();

			if (chartsyRegistred)
			{
				if (name != null)
					lblMessage.setText(name + ", thank you for the registration.");
				else
					lblMessage.setText("Thank you for the registration.");
				btnRegister.setVisible(false);
				btnRemind.setText("Close");
				btnRemind.setEnabled(true);
			}
			else
			{
				lblMessage.setText("Error, could not register. Check your username and password.");
			}
        } 
		catch (IOException ex)
		{
            LOG.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

	private boolean checkMrSwingRegistration()
	{
		String url = "http://www.mrswing.com/chartsy/companyname.php?symbol=VODE.DE";
		BufferedReader in = null;
		HttpClient client = ProxyManager.getDefault().getHttpClient();
		GetMethod method = new GetMethod(url);

		method.setFollowRedirects(true);
		List<Cookie> list = getMrSwingCookies(url);
		for (Cookie cookie : list)
			client.getState().addCookie(cookie);

		try
		{
			client.executeMethod(method);
			in = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));

			if (in.readLine().equals("OK"))
			{
				in.close();
				method.releaseConnection();
				return true;
			}
			else
			{
				in.close();
				method.releaseConnection();
				return false;
			}
		}
		catch (IOException ex)
		{
			LOG.log(Level.SEVERE, null, ex);
			method.releaseConnection();
			if (in != null)
			{
				try { in.close(); }
                catch (IOException io) { LOG.log(Level.SEVERE, null, io); }
			}
			return false;
		}
	}

	private List<Cookie> getMrSwingCookies(String url)
	{
		List<Cookie> list = new ArrayList<Cookie>();

		String username = txtUsername.getText();
		String password = new String(txtPassword.getPassword());

		if (username != null && password != null)
		{
			NameValuePair[] data =
            {
                new NameValuePair("amember_login", username),
                new NameValuePair("amember_pass", password)
            };

			HttpClient client = ProxyManager.getDefault().getHttpClient();
			PostMethod method = new PostMethod(url);
			method.setRequestBody(data);

			try
			{
				int responce = client.executeMethod(method);
				if (responce != HttpStatus.SC_NOT_IMPLEMENTED)
				{
					for (Cookie cookie : client.getState().getCookies())
					{
						if (usedCookies.contains(cookie.getName()))
							list.add(cookie);
					}
				}
				method.releaseConnection();
			}
			catch (IOException ex)
			{
				LOG.log(Level.SEVERE, null, ex);
			}
		}

		return list;
	}

	private int checkStockScanPRORegistration()
	{
		HttpClient client = ProxyManager.getDefault().getHttpClient();
		GetMethod method = new GetMethod("http://www.stockscanpro.com/index.php");
		int id = 0;

		try
		{
			method.setQueryString(new NameValuePair[]
			{
				new NameValuePair("option", "com_chartsy"),
				new NameValuePair("view", "checkregistration"),
				new NameValuePair("format", "raw"),
				new NameValuePair("username", txtUsername.getText()),
				new NameValuePair("passwd", new String(txtPassword.getPassword()))
			});

			client.executeMethod(method);
			id = Integer.parseInt(method.getResponseBodyAsString());
			method.releaseConnection();
		}
		catch (IOException ex)
		{
			LOG.log(Level.SEVERE, null, ex);
		}

		return id;
	}

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterForm dialog = new RegisterForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

	private List<String> usedCookies = new ArrayList<String>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnRemind;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

}
