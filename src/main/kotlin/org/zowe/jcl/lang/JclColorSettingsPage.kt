/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class JclColorSettingsPage: ColorSettingsPage {
  companion object {
    private val DESCRIPTORS = arrayOf(
      AttributesDescriptor("Comment", JclSyntaxHighlighter.COMMENT),
      AttributesDescriptor("In-stream data", JclSyntaxHighlighter.INSTREAM_TEXT),
      AttributesDescriptor("Start of line", JclSyntaxHighlighter.LINE_START),
      AttributesDescriptor("Operator", JclSyntaxHighlighter.OPERATOR),
      AttributesDescriptor("Operator name", JclSyntaxHighlighter.OPERATOR_NAME),
      AttributesDescriptor("Parameter name", JclSyntaxHighlighter.PARAM_NAME),
      AttributesDescriptor("Parameter value", JclSyntaxHighlighter.PARAM_VALUE),
      AttributesDescriptor("String", JclSyntaxHighlighter.STRING),
      AttributesDescriptor("Sequence numbers", JclSyntaxHighlighter.SEQUENCE_NUMBERS)
    )
  }

  override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

  override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

  override fun getDisplayName(): String = "JCL"

  override fun getIcon(): Icon = JclIcons.FILE

  override fun getHighlighter(): SyntaxHighlighter = JclSyntaxHighlighter()

  override fun getDemoText(): String =
    "//GENDATA7 JOB 1,CHRIS,MSGCLASS=X                                       seqNumb\n" +
    "//         EXEC PGM=IEBDG\n" +
    "//* THIS IS DEMO COMMENT\n" +
    "//SYSPRINT DD SYSOUT=*\n" +
    "//OUT    DD DISP=(NEW,CATLG),DSN=CHRIS.TEST.DATA,UNIT=3390,\n" +
    "//   VOL=SER=WORK01,SPACE=(CYL,(10,1)),\n" +
    "//   DCB=(RECFM=FB,LRECL=80,BLKSIZE=8000)\n" +
    "//SYSIN    DD *\n" +
    "  DSD OUTPUT=(OUT)\n" +
    "  FD NAME=FIELD1,LENGTH=30,FORMAT=AL,ACTION=RP\n" +
    "  FD NAME=FIELD2,LENGTH=10,PICTURE=10,'TEST DATA '\n" +
    "  FD NAME=FIELD3,LENGTH=10,FORMAT=RA\n" +
    "  CREATE QUANTITY=90000,NAME=(FIELD1,FIELD2,FIELD3)\n" +
    "  END\n" +
    "/*"

  override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null
}
