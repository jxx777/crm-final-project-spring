package itschool.crmfinalproject.entity.app.event;

import lombok.Getter;

@Getter
public enum SubscriptionEnum {
    BASIC_PACKAGE("Basic Package", 1),
    PREMIUM_PACKAGE("Premium Package", 6),
    STANDARD_PACKAGE("Standard Package", 3),
    ULTIMATE_PACKAGE("Ultimate Package", 12);

    private final String description;
    private final int durationMonths;

    SubscriptionEnum(String description, int durationMonths) {
        this.description = description;
        this.durationMonths = durationMonths;
    }
}