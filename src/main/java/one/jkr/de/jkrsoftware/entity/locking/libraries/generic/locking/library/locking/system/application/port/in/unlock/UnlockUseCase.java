package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock;

import lombok.NonNull;

public interface UnlockUseCase {

    boolean unlock(@NonNull UnlockCommand unlockCommand);

    boolean unlock(@NonNull ForceUnlockCommand forceUnlockCommand);

}
