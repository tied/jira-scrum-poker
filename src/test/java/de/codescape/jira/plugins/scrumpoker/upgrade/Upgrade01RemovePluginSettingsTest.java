package de.codescape.jira.plugins.scrumpoker.upgrade;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import de.codescape.jira.plugins.scrumpoker.service.ScrumPokerSettingService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static de.codescape.jira.plugins.scrumpoker.upgrade.AbstractUpgradeTask.SCRUM_POKER_PLUGIN_KEY;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Upgrade01RemovePluginSettingsTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private PluginSettingsFactory pluginSettingsFactory;

    @Mock
    private ScrumPokerSettingService scrumPokerSettingService;

    @InjectMocks
    private Upgrade01RemovePluginSettings upgrade;

    @Mock
    private PluginSettings pluginSettings;

    @Test
    public void shouldDeleteSessionTimeoutIfItExistsInPluginSettings() {
        withGlobalSettingsFactory();
        when(pluginSettings.get(SCRUM_POKER_PLUGIN_KEY + ".sessionTimeout")).thenReturn("12");
        upgrade.doUpgrade();
        verify(pluginSettings).remove(SCRUM_POKER_PLUGIN_KEY + ".sessionTimeout");
    }

    @Test
    public void shouldDeleteStoryPointFieldIfItExistsInPluginSettings() {
        withGlobalSettingsFactory();
        when(pluginSettings.get(SCRUM_POKER_PLUGIN_KEY + ".storyPointField")).thenReturn("customfield-10006");
        upgrade.doUpgrade();
        verify(pluginSettings).remove(SCRUM_POKER_PLUGIN_KEY + ".storyPointField");
    }

    @Test
    public void shouldPersistStoryPointFieldIfExistsInPluginSettings() {
        withGlobalSettingsFactory();
        when(pluginSettings.get(SCRUM_POKER_PLUGIN_KEY + ".storyPointField")).thenReturn("customfield-10006");
        upgrade.doUpgrade();
        verify(scrumPokerSettingService).persistStoryPointField("customfield-10006");
    }

    @Test
    public void shouldPersistSessionTimeoutIfExistInPluginSettings() {
        withGlobalSettingsFactory();
        when(pluginSettings.get(SCRUM_POKER_PLUGIN_KEY + ".sessionTimeout")).thenReturn("12");
        upgrade.doUpgrade();
        verify(scrumPokerSettingService).persistSessionTimeout(12);
    }

    @Test
    public void shouldReturnCorrectBuildNumber() {
        assertThat(upgrade.getBuildNumber(), is(equalTo(1)));
    }

    @Test
    public void shouldReturnShortDescriptionWithLessThan50Characters() {
        assertThat(upgrade.getShortDescription().length(), is(lessThan(50)));
    }

    private void withGlobalSettingsFactory() {
        when(pluginSettingsFactory.createGlobalSettings()).thenReturn(pluginSettings);
    }

}
