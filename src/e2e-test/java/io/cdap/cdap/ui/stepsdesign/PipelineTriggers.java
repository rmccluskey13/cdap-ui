/*
 * Copyright © 2022 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.cdap.ui.stepsdesign;

import io.cdap.cdap.ui.utils.Commands;
import io.cdap.cdap.ui.utils.Helper;
import io.cdap.e2e.utils.ElementHelper;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.WaitHelper;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class PipelineTriggers {
  public static String simpleTriggerName = "simple_trigger_test";
  public static String complexTriggerName = "complex_trigger_test";

  @Then("Deploy pipeline {string} with pipeline JSON file {string}")
  public void deployPipelineFromJson(String pipelineName, String pipelineJSONfile) {
    Helper.deployAndTestPipeline(pipelineJSONfile, pipelineName);
  }

  @Then("Open inbound trigger and set and delete a simple trigger when {string} succeeds")
  public void openInboundTriggerAndSetAndDeleteASimpleTrigger(String sourcePipeline) {
    ElementHelper.clickOnElement(Helper.locateElementByTestId("inbound-triggers-toggle"));
    WebElement triggerNameInputField = Helper.locateElementByCssSelector(
      Helper.getCssSelectorByDataTestId("trigger-name-text-field") + " input"
    );
    ElementHelper.clearElementValue(triggerNameInputField);
    ElementHelper.sendKeys(triggerNameInputField, simpleTriggerName);
    ElementHelper.clickOnElement(Helper.locateElementByTestId(sourcePipeline + "-enable-trigger-btn"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId("enable-group-trigger-btn"));
    Helper.isElementExists(Helper.getCssSelectorByDataTestId(simpleTriggerName + "-collapsed"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId(simpleTriggerName + "-collapsed"));
    Helper.isElementExists(Helper.getCssSelectorByDataTestId(simpleTriggerName + "-expanded"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId(simpleTriggerName + "-disable-trigger-btn"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId("Delete"));
    Assert.assertFalse(Helper.isElementExists(Helper.getCssSelectorByDataTestId(simpleTriggerName + "-collapsed")));
    ElementHelper.clickOnElement(Helper.locateElementByTestId("inbound-triggers-toggle"));
  }

  private WebElement getSourceRuntimeArgElement(int index) {
    String selector = Helper.getCssSelectorByDataTestId("row-" + index) +  " " +
      Helper.getCssSelectorByDataTestId("runtime-arg-of-trigger") +  " select";
    return Helper.locateElementByCssSelector(selector);
  }

  private WebElement getTargetRuntimeArgElement(int index) {
    String selector = Helper.getCssSelectorByDataTestId("row-" + index) +  " " +
      Helper.getCssSelectorByDataTestId("runtime-arg-of-triggered") +  " select";
    return Helper.locateElementByCssSelector(selector);
  }

  private void selectRuntimeArg(WebElement element, String value) {
    Select select = new Select(element);
    select.selectByVisibleText(value);
  }

  @Then("Open inbound trigger and set a complex trigger when {string} succeeds")
  public void openInboundTriggerAndSetAComplexTrigger(String sourcePipeline) {
    ElementHelper.clickOnElement(Helper.locateElementByTestId("inbound-triggers-toggle"));
    WebElement triggerNameInputField = Helper.locateElementByCssSelector(
      "div[data-testid='trigger-name-text-field'] input"
    );
    ElementHelper.clearElementValue(triggerNameInputField);
    ElementHelper.sendKeys(triggerNameInputField, complexTriggerName);

    ElementHelper.clickOnElement(Helper.locateElementByTestId(sourcePipeline + "-collapsed"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId(sourcePipeline + "-trigger-config-btn"));

    selectRuntimeArg(getSourceRuntimeArgElement(0), "source_path");
    selectRuntimeArg(getTargetRuntimeArgElement(0), "source_path");
    selectRuntimeArg(getSourceRuntimeArgElement(1), "sink_path");
    selectRuntimeArg(getTargetRuntimeArgElement(1), "sink_path");

    ElementHelper.clickOnElement(Helper.locateElementByTestId("configure-and-enable-trigger-btn"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId("enable-group-trigger-btn"));

    ElementHelper.clickOnElement(Helper.locateElementByTestId(complexTriggerName + "-collapsed"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId(sourcePipeline + "-view-payload-btn"));

    WebElement sourceArg0 = getSourceRuntimeArgElement(0);
    Assert.assertEquals("source_path", sourceArg0.getAttribute("value"));
    WebElement sourceArg1 = getSourceRuntimeArgElement(1);
    Assert.assertEquals("sink_path", sourceArg1.getAttribute("value"));

    WebElement targetArg0 = getTargetRuntimeArgElement(0);
    Assert.assertEquals("source_path", targetArg0.getAttribute("value"));
    WebElement targetArg1 = getTargetRuntimeArgElement(1);
    Assert.assertEquals("sink_path", targetArg1.getAttribute("value"));

    ElementHelper.clickOnElement(Helper.locateElementByTestId("close-payload-config-modal"));
  }

  @Then("Ensure outbound trigger to {string} exists")
  public void ensureOutboundTriggerExists(String triggeredPipeline) {
    ElementHelper.clickOnElement(Helper.locateElementByTestId("outbound-triggers-toggle"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId(triggeredPipeline + "-triggered-collapsed"));
    // The animation triggered on the line above takes some time to complete,
    // so wait for text at the bottom to be visible
    WebElement outboundTrigger = Helper.locateElementByTestId(triggeredPipeline + "-triggered-expanded");
    SeleniumDriver.getWaitDriver(3).until(ExpectedConditions.textToBePresentInElement(outboundTrigger, "Succeeds"));
    Assert.assertTrue(ElementHelper.getElementText(outboundTrigger).contains("Succeeds"));
  }

  @Then("Cleanup complex trigger")
  public void cleanupComplexTrigger() {
    ElementHelper.clickOnElement(Helper.locateElementByTestId("inbound-triggers-toggle"));

    ElementHelper.clickOnElement(Helper.locateElementByTestId(complexTriggerName + "-collapsed"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId(complexTriggerName + "-disable-trigger-btn"));
    ElementHelper.clickOnElement(Helper.locateElementByTestId("Delete"));
    Assert.assertFalse(Helper.isElementExists(Helper.getCssSelectorByDataTestId(complexTriggerName + "-collapsed")));
    ElementHelper.clickOnElement(Helper.locateElementByTestId("inbound-triggers-toggle"));
  }
}
