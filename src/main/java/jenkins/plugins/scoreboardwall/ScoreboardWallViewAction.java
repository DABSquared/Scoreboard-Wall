/*
 * The MIT License
 * 
 * Copyright (c) 2013, Daniel Brooks
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jenkins.plugins.scoreboardwall;

import hudson.Extension;
import hudson.PluginWrapper;
import hudson.model.Action;
import hudson.model.Describable;
import hudson.model.Api;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

/**
 * Display the ScoreboardWall action on each view.
 * 
 * @author Daniel Brooks
 */
@ExportedBean
public class ScoreboardWallViewAction implements Action, Describable<ScoreboardWallViewAction> {

	public final static String SHORT_NAME = "scoreboardwall";
	
	public final static String DISPLAY_NAME = "ScoreBoardWall";
	
	public Api getApi() {
		return new Api(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Action#getDisplayName()
	 */
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Action#getIconFileName()
	 */
	public String getIconFileName() {
		return "monitor.png";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Action#getUrlName()
	 */
	public String getUrlName() {
		return SHORT_NAME;
	}

	@Exported
	public int getPollInterval() {
		return getDescriptor().getPollInterval();
	}

	@Exported
	public boolean getShowGravatar() {
		return getDescriptor().getShowGravatar();
	}

	@Exported
	public boolean getShowUsername() {
		return getDescriptor().getShowUsername();
	}

	@Exported
	public boolean getShowBuildNumber() {
		return getDescriptor().getShowBuildNumber();
	}
	
	@Exported
	public String getVersion() {
		return getPluginWrapper().getVersion();
	}
	
	@Exported
	public String getRootUrl() {
		return Jenkins.getInstance().getRootUrlFromRequest();
	}
	
	public ScoreboardWallViewActionDescriptor getDescriptor() {
		return ScoreboardWallViewActionDescriptor.class.cast(Jenkins.getInstance().getDescriptorOrDie(getClass()));
	}
	
	public PluginWrapper getPluginWrapper() {
		return Jenkins.getInstance().getPlugin(SHORT_NAME).getWrapper();
	}

	@Extension
	public static final class ScoreboardWallViewActionDescriptor extends Descriptor<ScoreboardWallViewAction> {

		private int pollInterval = 5;
		
		private boolean showGravatar = false;
		
		private boolean showBuildNumber = false;
		
		private boolean showUsername = false;

		@Override
		public String getDisplayName() {
			return DISPLAY_NAME;
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
			req.bindJSON(this, json.getJSONObject("scoreboardwall"));
			save();
			return true;
		}

		public int getPollInterval() {
			return pollInterval;
		}

		public void setPollInterval(int pollInterval) {
			this.pollInterval = pollInterval;
		}

		public boolean getShowGravatar() {
			return showGravatar;
		}

		public void setShowGravatar(boolean showGravatar) {
			this.showGravatar = showGravatar;
		}

		public boolean getShowBuildNumber() {
			return showBuildNumber;
		}

		public void setShowBuildNumber(boolean showJobNumber) {
			this.showBuildNumber = showJobNumber;
		}

		public boolean getShowUsername() {
			return showUsername;
		}

		public void setShowUsername(boolean showUsername) {
			this.showUsername = showUsername;
		}

	}

}
