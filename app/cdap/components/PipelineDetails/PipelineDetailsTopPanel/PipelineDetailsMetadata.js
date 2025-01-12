/*
 * Copyright © 2018 Cask Data, Inc.
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

import PropTypes from 'prop-types';
import React from 'react';
import { connect, Provider } from 'react-redux';
import T from 'i18n-react';
import styled from 'styled-components';
import PipelineDetailStore from 'components/PipelineDetails/store';
import Tags from 'components/shared/Tags';
import IconSVG from 'components/shared/IconSVG';
import Popover from 'components/shared/Popover';
import { GLOBALS } from 'services/global-constants';
import { Chip } from '@material-ui/core';

const PREFIX = 'features.PipelineDetails.TopPanel';

const StyledSpan = styled.span`
  margin-left: 50px;
`;

const StyledChip = styled(Chip)`
  height: 20px;
`;

const mapStateToPipelineTagsProps = (state) => {
  let { name } = state;
  return {
    entity: {
      id: name,
      type: 'application',
    },
    showCountLabel: false,
    isNativeLink: true,
  };
};

const ConnectedPipelineTags = connect(mapStateToPipelineTagsProps)(Tags);

const mapStateToPipelineDetailsMetadataProps = (state) => {
  let { name, artifact, version, description, sourceControlMeta } = state;
  return {
    name,
    artifactName: artifact.name,
    version,
    description,
    sourceControlMeta,
  };
};

const PipelineDetailsMetadata = ({
  name,
  artifactName,
  version,
  description,
  sourceControlMeta,
}) => {
  return (
    <div className="pipeline-metadata">
      <div className="pipeline-type-name-version">
        <span className="pipeline-type">
          {artifactName === GLOBALS.etlDataPipeline ? (
            <IconSVG name="icon-ETLBatch" />
          ) : (
            <IconSVG name="icon-sparkstreaming" />
          )}
        </span>
        <h1 className="pipeline-name" title={name}>
          {name}
        </h1>
        <span className="pipeline-description">
          <Popover
            target={() => <IconSVG name="icon-info-circle" />}
            showOn="Hover"
            placement="bottom"
          >
            {description}
          </Popover>
        </span>
        {sourceControlMeta && sourceControlMeta.fileHash && (
          <StyledSpan>
            <StyledChip
              variant="outlined"
              label={T.translate('features.SourceControlManagement.table.connected')}
            />
          </StyledSpan>
        )}
        <span className="pipeline-version">{T.translate(`${PREFIX}.version`, { version })}</span>
      </div>
      <div className="pipeline-tags">
        <ConnectedPipelineTags />
      </div>
    </div>
  );
};

PipelineDetailsMetadata.propTypes = {
  name: PropTypes.string,
  artifactName: PropTypes.string,
  version: PropTypes.string,
  description: PropTypes.string,
  sourceControlMeta: PropTypes.object,
};

const ConnectedPipelineDetailsMetadata = connect(mapStateToPipelineDetailsMetadataProps)(
  PipelineDetailsMetadata
);

const ProvidedPipelineDetailsMetadata = () => {
  return (
    <Provider store={PipelineDetailStore}>
      <ConnectedPipelineDetailsMetadata />
    </Provider>
  );
};

export default ProvidedPipelineDetailsMetadata;
