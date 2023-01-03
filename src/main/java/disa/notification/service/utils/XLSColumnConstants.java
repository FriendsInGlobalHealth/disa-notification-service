package disa.notification.service.utils;

public interface XLSColumnConstants {
    int FIRST_ROW = 0;
    int FIRST_COL = 0;
    int SECOND_ROW = 1;
    int THIRD_ROW = 2;
    String[] VIRAL_RESULTS_HEADER = { "REQUEST_ID", "NID", "Distrito", "Codigo da US", "Nome da US",
            "Data de Entrada", "Data de Sincronizacao", "Estado", "Motivo de Não envio", "Observacoes" };
    int COL0_REQUEST_ID = 0;
    int COL1_NID = 1;
    int COL2_DISTRICT = 2;
    int COL3_HEALTH_FACILITY_CODE = 3;
    int COL4_HEALTH_FACILITY_NAME = 4;
    int COL5_CREATED_AT = 5;
    int COL6_UPDATED_AT = 6;
    int COL7_VIRAL_RESULT_STATUS = 7;
    int COL8_VIRAL_RESULT_STATUS_CAUSE = 8;

    int COL9_VIRAL_RESULT_STATUS_CAUSE = 9;

    enum ResultsByHFSummary {
        DISTRICT("Distrito"),
        HEALTH_FACILITY_CODE("Código da US"),
        HEALTH_FACILITY_NAME("Nome da US"),
        TOTAL_RECEIVED("Total Recebidos"),
        TOTAL_PROCESSED("No. Processados "),
        TOTAL_PENDING("No. Pendentes"),
        NOT_PROCESSED_NO_RESULT("No. Sem Resultados"),
        NOT_PROCESSED_NID_NOT_FOUND("No. NID nao encontrado"),
        NOT_PROCESSED_DUPLICATED_NID("No. NID duplicado"),
        NOT_PROCESSED_DUPLICATED_REQUEST_ID("No. ID da requisição duplicado"),
        NOT_PROCESSED_FLAGGED_FOR_REVIEW("No. Sinalizado para Revisão");

        private final String header;

        ResultsByHFSummary(String header) {
            this.header = header;
        }

        public String header() {
            return header;
        }
    }

    int COL0_DISTRICT = 0;
    int COL1_HEALTH_FACILITY_CODE = 1;
    int COL2_HEALTH_FACILITY_NAME = 2;
    int COL3_TOTAL_RECEIVED = 3;
    int COL4_TOTAL_PROCESSED = 4;
    int COL5_TOTAL_PENDING= 5;
    int COL6_NOT_PROCESSED_NO_RESULT= 6;
    int COL7_NOT_PROCESSED_NID_NOT_FOUND= 7;
    int COL8_NOT_PROCESSED_DUPLICATED_NID=8;
    int COL9_NOT_PROCESSED_DUPLICATED_REQUEST_ID=9;
    int COL10_NOT_PROCESSED_FLAGGED_FOR_REVIEW = 10;

    String[] UNSYNCRONIZED_VIRAL_RESULTS_HEADER = { "REQUEST_ID", "NID", "Distrito", "Codigo da US", "Nome da US",
            "Data de Envio", "Estado" };
    int COL5_SENT_DATE = 5;
    int COL6_STATUS = 6;

    String VIRAL_RESULT_SUMMARY_TITLE = "Resultados de CV Recebidos no Servidor de Integração no Período de Domingo (%s) a Sábado (%s) da semana enterior por US";
    String VIRAL_RESULT_TITLE = "Resultados de CV Recebidos no Servidor de Integração no Periodo de Domingo (%s) a Sábado (%s) da semana anterior";
    String NOT_SYNCRONIZED_VIRAL_RESULTS = "Resultados de CV Pendentes no Servidor de Integração há mais de 48 horas por NID (Cumulativo)";
    String PENDING_VIRAL_RESULT_SUMMARY = "Resultados de CV Pendentes no Servidor de Integração há mais de 48 horas por US ";

    String STATS_TITLE = "Resultados de CVs por Distrito recebidos no Período de Domingo (%s) a Sábado (%s) da semana anterior";

    String[] PENDING_VIRAL_RESULT_SUMMARY_HEADER = { "Distrito", "Código da US", "Nome da US", "No. CVs Pendentes",
            "Data da Última Sincronização" };
    int COL3_TOTAL_PENDING = 3;
    int COL4_LAST_SYNC_DATE = 4;

    String[] DICTIONARY_HEADER = { "Variável", "Definição" };

    enum ResultsByDistrictSummary {
        DISTRICT("Distrito"),
        TOTAL_PROCESSED("No. Processados "),
        PERCENTAGE_PROCESSED("% Processados "),
        TOTAL_PENDING("No. Pendentes"),
        PERCENTAGE_PENDING("% Pendentes "),
        NOT_PROCESSED_NO_RESULT("No. Sem Resultados"),
        PERCENTAGE_NOT_PROCESSED_NO_RESULT("% Sem Resultados "),
        NOT_PROCESSED_NID_NOT_FOUND("No. NID não encontrado"),
        PERCENTAGE_NOT_PROCESSED_NID_NOT_FOUND("% NID não encontrado"),
        NOT_PROCESSED_DUPLICATED_NID("No. NID duplicado"),
        PERCENTAGE_NOT_PROCESSED_DUPLICATED_NID ("% NID duplicado"),
        NOT_PROCESSED_DUPLICATED_REQUEST_ID ("No. ID da requisição duplicado"),
        PERCENTAGE_NOT_PROCESSED_DUPLICATED_REQUEST_ID ("% ID da requisição duplicado"),
        NOT_PROCESSED_FLAGGED_FOR_REVIEW ("No. Sinalizado para Revisão"),
        PERCENTAGE_NOT_PROCESSED_FLAGGED_FOR_REVIEW ("% Sinalizado para Revisão"),
        TOTAL_RECEIVED ("Total Recebidos");

        private final String header;

        ResultsByDistrictSummary(String header) {
            this.header = header;
        }

        public String header() {
            return header;
        }
    }

    String[] VIRAL_STAT_HEADER = {
            "Distrito",
            "No. Processados ",
            "% Processados ",
            "No. Pendentes",
            "% Pendentes ",
            "No. Sem Resultados",
            "% Sem Resultados ",
            "No. NID não encontrado",
            "% NID não encontrado",
            "No. NID duplicado",
            "% NID duplicado",
            "No. ID da requisição duplicado",
            "% ID da requisição duplicado",
            "No. Sinalizado para Revisão",
            "% Sinalizado para Revisão",
            "Total Recebidos" };

    int STAT0_DISTRICT = 0;

    int STAT1_TOTAL_PROCESSED = 1;

    int STAT2_PERCENTAGE_PROCESSED = 2;

    int STAT3_TOTAL_PENDING = 3;

    int STAT4_PERCENTAGE_PENDING = 4;

    int STAT5_NOT_PROCESSED_NO_RESULT = 5;

    int STAT6_PERCENTAGE_NOT_PROCESSED_NO_RESULT = 6;

    int STAT7_NOT_PROCESSED_NID_NOT_FOUND = 7;

    int STAT8_PERCENTAGE_NOT_PROCESSED_NID_NOT_FOUND = 8;

    int STAT9_NOT_PROCESSED_DUPLICATED_NID = 9;

    int STAT10_PERCENTAGE_NOT_PROCESSED_DUPLICATED_NID = 10;

    int STAT11_NOT_PROCESSED_DUPLICATED_REQUEST_ID = 11;

    int STAT12_PERCENTAGE_NOT_PROCESSED_DUPLICATED_REQUEST_ID = 12;

    int STAT13_NOT_PROCESSED_FLAGGED_FOR_REVIEW = 13;

    int STAT14_PERCENTAGE_NOT_PROCESSED_FLAGGED_FOR_REVIEW = 14;

    int STAT15_TOTAL_RECEIVED = 15;

}
