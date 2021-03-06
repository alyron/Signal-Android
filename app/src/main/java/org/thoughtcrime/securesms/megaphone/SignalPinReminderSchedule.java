package org.thoughtcrime.securesms.megaphone;

import org.thoughtcrime.securesms.keyvalue.SignalStore;
import org.thoughtcrime.securesms.util.FeatureFlags;

final class SignalPinReminderSchedule implements MegaphoneSchedule {

  @Override
  public boolean shouldDisplay(int seenCount, long lastSeen, long firstVisible, long currentTime) {
    if (!SignalStore.kbsValues().hasPin()) {
      return false;
    }

    if (!FeatureFlags.pinsForAll()) {
      return false;
    }

    if (!SignalStore.pinValues().arePinRemindersEnabled()) {
      return false;
    }

    long lastSuccessTime = SignalStore.pinValues().getLastSuccessfulEntryTime();
    long interval        = SignalStore.pinValues().getCurrentInterval();

    return currentTime - lastSuccessTime >= interval;
  }
}
