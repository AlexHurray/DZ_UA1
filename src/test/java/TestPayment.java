import org.testng.annotations.*;

public class TestPayment extends BaseRunner {
    @Test
    public void testPayment() {
        GoogleMainPage googleMainPage = new GoogleMainPage(driver, true);
        GoogleSearchResultsPage googleSearchResultsPage = googleMainPage.search("tinkoff");

        TinkoffPaymentsMobilePage tinkoffPaymentsMobilePage = googleSearchResultsPage.openFirstLink().openPayments().
                openPaymentsMobile();

        tinkoffPaymentsMobilePage.validPhoneField();
        tinkoffPaymentsMobilePage.validSumField();
    }

    @Test
    public void testAdditional() {
        // 1. Открыть https://www.tinkoff.ru/
        TinkoffMainPage mainPage = new TinkoffMainPage(driver, true);

        // 2. Из верхнего меню по нажатию "Мобайл" выполнить переход на страницу Тинькофф Мобайл
        TinkoffMobilePage mobilePage = mainPage.openMobile();

        // 3. Выполнить переход на вкладку Тарифы
        TinkoffMobilePageTariffsTab tariffsTab = mobilePage.openTafiffs();

        // 4. Выполнить скролл до появления всплывающего желтого окошка (с кнопкой "Оформить"
        // и суммой ежемесячного платежа). Запомнить значение суммы стартового предложения в локальной переменной.
        tariffsTab.showStickButton();
        int price = tariffsTab.getPrice();

        // 5. Обнулить все пакеты и отключить все дополнительные услуги
        tariffsTab.switchOffAll();

        // Убедится в том, что сумма предложения станет 0 рублей
        tariffsTab.checkIntEquals(() -> tariffsTab.getPrice(), 0);

        // а кнопка "Получить SIM-карту" станет не активна
        tariffsTab.checkIsEnableGetSIM();

        // 6. Обновить страницу и убедиться, что стартовое предложение вернулось в первоначальное значение (шаг 4)
        tariffsTab.refreshPage();
        tariffsTab.showStickButton();
        tariffsTab.checkIntEquals(() -> tariffsTab.getPrice(), price);

        // 7. Выбрать безлимитного пакета интернета и убедиться, что все элементы из блока
        // "Другие безлимитных сервисов" скрылись
        tariffsTab.checkHidingOtherServices();

        // 8. Убедиться в том, что безлимитный пакет интернета дороже набора из пакета 15 ГБ +
        // сумма всех безлимитных сервисов
        tariffsTab.checkUnlim();

        // 9. Проверить скролл по нажатию кнопки "Заказать SIM-карту"
        tariffsTab.returnToMainTab();
        mobilePage.checkScroll();
    }
}
