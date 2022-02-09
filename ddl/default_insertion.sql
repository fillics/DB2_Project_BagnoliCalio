INSERT INTO dbtelco.service (typeOfService) VALUES ('Fixed Phone');
INSERT INTO dbtelco.service (typeOfService, numMinutes, numSMS, feeExtraMinute, feeExtraSMSs) VALUES ('Mobile Phone', '100', '50', '0.5', '0.2');
INSERT INTO dbtelco.service (typeOfService, numGigabytes, feeForExtraGigabytes) VALUES ('Fixed Internet', '30', '0.7');
INSERT INTO dbtelco.service (typeOfService, numGigabytes, feeForExtraGigabytes) VALUES ('Mobile Internet', '10', '2');

INSERT INTO dbtelco.validityperiod (numOfMonths, monthlyFee) VALUES ('12', '20');
INSERT INTO dbtelco.validityperiod (numOfMonths, monthlyFee) VALUES ('24', '18');
INSERT INTO dbtelco.validityperiod (numOfMonths, monthlyFee) VALUES ('36', '15');

INSERT INTO `dbtelco`.`user` (`username`, `password`, `email`) VALUES ('a', 'a', 'a');
INSERT INTO `dbtelco`.`employee` (`username`, `password`, `email`) VALUES ('e', 'e', 'e');
