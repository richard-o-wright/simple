<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:param name="sort"/>
<xsl:param name="page"/>
<xsl:param name="personId"/>
<xsl:param name="month"/>
<xsl:param name="jobId"/>
<xsl:output method="html" />

<xsl:template match="/">
    <table class="SItableBorderColor" cellspacing="0" cellpadding="3" border="1" width="600px">
        <xsl:call-template name="header"/>
        <xsl:apply-templates select="*/*">
            <xsl:sort select="*[$sort]" />
        </xsl:apply-templates>
    </table>
</xsl:template>

<!-- creates the table headers from the tag names -->
    <xsl:template name="header">
        <tr>
            <xsl:for-each select="*/*[1]/*">
                <th class="SItableHeaderCell">
                    <a href="{$page}?sort={position()}&amp;personId={$personId}&amp;month={$month}&amp;jobId={$jobId}">
                        <xsl:choose>
                            <xsl:when test="local-name(.) = 'JOB_ID'">
                                <br/>
                            </xsl:when>
                            <xsl:when test="local-name(.) = 'HREF'">
                                <br/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="local-name(.)" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </a>
                </th>
            </xsl:for-each>
        </tr>
    </xsl:template>

<!-- creates a row for each child of root, -->
<!-- and cell for each grandchild of root -->
<xsl:template match="*/*">
    <tr>
        <xsl:for-each select="*">
            <td class="SItableNoneditCell1">
                <xsl:variable name="foo" select="."/>  
                <xsl:choose>                   
                   <xsl:when test="local-name(.) = 'JOB_ID'">
                       <xsl:variable name="jid" select="."/>
                       <a href="tasks.jsp?jobID={$jid}">View</a>
                   </xsl:when>
                   <xsl:when test="local-name(.) = 'HREF'">
                       <xsl:variable name="link" select="."/>
                       <a href="{$link}" target="top">SOW Document</a>
                   </xsl:when>
                  <xsl:otherwise> 
                    <xsl:value-of select="."/>
                  </xsl:otherwise>
                 </xsl:choose>               
            </td>
        </xsl:for-each>
    </tr>
</xsl:template>

</xsl:stylesheet>

