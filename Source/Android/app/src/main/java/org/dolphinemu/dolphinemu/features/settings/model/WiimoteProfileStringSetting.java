// SPDX-License-Identifier: GPL-2.0-or-later

package org.dolphinemu.dolphinemu.features.settings.model;

import androidx.annotation.NonNull;

import org.dolphinemu.dolphinemu.features.settings.utils.SettingsFile;

// This stuff is pretty ugly. It's a kind of workaround for certain controller settings
// not actually being available as game-specific settings.
public class WiimoteProfileStringSetting implements AbstractStringSetting
{
  private final int mPadID;

  private final String mSection;
  private final String mKey;
  private final String mDefaultValue;

  private final String mProfileKey;

  private final String mProfile;

  public WiimoteProfileStringSetting(String gameID, int padID, String section, String key,
          String defaultValue)
  {
    mPadID = padID;
    mSection = section;
    mKey = key;
    mDefaultValue = defaultValue;

    mProfileKey = SettingsFile.KEY_WIIMOTE_PROFILE + (padID + 1);

    mProfile = gameID + "_Wii" + padID;
  }

  @Override
  public boolean isOverridden(@NonNull Settings settings)
  {
    return settings.isWiimoteProfileEnabled(settings, mProfile, mProfileKey);
  }

  @Override
  public boolean isRuntimeEditable()
  {
    return false;
  }

  @Override
  public boolean delete(@NonNull Settings settings)
  {
    return settings.disableWiimoteProfile(settings, mProfileKey);
  }

  @NonNull @Override
  public String getString(@NonNull Settings settings)
  {
    if (settings.isWiimoteProfileEnabled(settings, mProfile, mProfileKey))
      return settings.getWiimoteProfile(mProfile, mPadID).getString(mSection, mKey, mDefaultValue);
    else
      return mDefaultValue;
  }

  @Override
  public void setString(@NonNull Settings settings, @NonNull String newValue)
  {
    settings.getWiimoteProfile(mProfile, mPadID).setString(mSection, mKey, newValue);

    settings.enableWiimoteProfile(settings, mProfile, mProfileKey);
  }
}
